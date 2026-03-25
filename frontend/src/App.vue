<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref } from "vue";

const params = new URLSearchParams(window.location.search);
const storeCode = ref(params.get("storeCode") || "STORE-001");
const agreementVersion = "v1.0.0";

const customerName = ref("");
const phone = ref("");
const submitting = ref(false);
const errorMessage = ref("");
const submitResult = ref(null);

const canvasRef = ref(null);
const drawing = ref(false);
const hasSigned = ref(false);
let context2d = null;

const canSubmit = computed(() => {
  return (
    customerName.value.trim().length > 0 &&
    /^1\d{10}$/.test(phone.value.trim()) &&
    hasSigned.value &&
    !submitting.value
  );
});

function initCanvas() {
  const canvas = canvasRef.value;
  if (!canvas) {
    return;
  }

  const ratio = window.devicePixelRatio || 1;
  const rect = canvas.getBoundingClientRect();
  canvas.width = Math.floor(rect.width * ratio);
  canvas.height = Math.floor(rect.height * ratio);

  context2d = canvas.getContext("2d");
  context2d.scale(ratio, ratio);
  context2d.lineWidth = 2;
  context2d.lineCap = "round";
  context2d.lineJoin = "round";
  context2d.strokeStyle = "#1e293b";
}

function pointFromEvent(event) {
  const canvas = canvasRef.value;
  const rect = canvas.getBoundingClientRect();
  const touch = event.touches?.[0] || event.changedTouches?.[0];
  if (touch) {
    return { x: touch.clientX - rect.left, y: touch.clientY - rect.top };
  }
  return { x: event.clientX - rect.left, y: event.clientY - rect.top };
}

function startDraw(event) {
  if (!context2d) {
    return;
  }
  event.preventDefault();
  drawing.value = true;
  const { x, y } = pointFromEvent(event);
  context2d.beginPath();
  context2d.moveTo(x, y);
}

function moveDraw(event) {
  if (!drawing.value || !context2d) {
    return;
  }
  event.preventDefault();
  const { x, y } = pointFromEvent(event);
  context2d.lineTo(x, y);
  context2d.stroke();
}

function endDraw(event) {
  if (!drawing.value) {
    return;
  }
  event.preventDefault();
  drawing.value = false;
  hasSigned.value = true;
}

function clearSignature() {
  const canvas = canvasRef.value;
  if (!canvas || !context2d) {
    return;
  }
  context2d.clearRect(0, 0, canvas.width, canvas.height);
  hasSigned.value = false;
}

async function fetchDetail(id) {
  const response = await fetch(`/api/signatures/${id}`);
  if (!response.ok) {
    throw new Error("查询提交结果失败");
  }
  return await response.json();
}

async function submitForm() {
  errorMessage.value = "";
  submitResult.value = null;

  if (!canSubmit.value) {
    errorMessage.value = "请完整填写信息并完成签字";
    return;
  }

  submitting.value = true;
  try {
    const payload = {
      storeCode: storeCode.value.trim(),
      customerName: customerName.value.trim(),
      phone: phone.value.trim(),
      agreementVersion,
      signatureDataUrl: canvasRef.value.toDataURL("image/png")
    };

    const response = await fetch("/api/signatures", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });
    const result = await response.json();
    if (!response.ok) {
      throw new Error(result.message || "提交失败");
    }

    submitResult.value = await fetchDetail(result.id);
  } catch (error) {
    errorMessage.value = error.message || "提交失败";
  } finally {
    submitting.value = false;
  }
}

onMounted(async () => {
  await nextTick();
  initCanvas();
  window.addEventListener("resize", initCanvas);
});

onBeforeUnmount(() => {
  window.removeEventListener("resize", initCanvas);
});
</script>

<template>
  <main class="page">
    <section class="card">
      <h1>门店扫码签字确认</h1>
      <p class="desc">
        请阅读协议说明后完成签名确认。本页面用于演示完整上线链路。
      </p>

      <div class="agreement">
        <h2>协议说明</h2>
        <p>1. 本人确认已知晓本次服务内容及费用说明。</p>
        <p>2. 本人确认联系方式真实有效，可用于服务回访。</p>
        <p>3. 本人确认以上签名与本人身份一致。</p>
        <p class="meta">协议版本：{{ agreementVersion }}</p>
      </div>

      <div class="form-item">
        <label>门店编码</label>
        <input v-model="storeCode" placeholder="例如：STORE-001" />
      </div>

      <div class="form-item">
        <label>姓名</label>
        <input v-model="customerName" maxlength="64" placeholder="请输入姓名" />
      </div>

      <div class="form-item">
        <label>手机号</label>
        <input v-model="phone" maxlength="11" inputmode="numeric" placeholder="请输入11位手机号" />
      </div>

      <div class="form-item">
        <div class="sign-head">
          <label>手写签字</label>
          <button type="button" class="text-btn" @click="clearSignature">清空签字</button>
        </div>
        <canvas
          ref="canvasRef"
          class="sign-canvas"
          @mousedown="startDraw"
          @mousemove="moveDraw"
          @mouseup="endDraw"
          @mouseleave="endDraw"
          @touchstart="startDraw"
          @touchmove="moveDraw"
          @touchend="endDraw"
        />
      </div>

      <button class="submit-btn" :disabled="!canSubmit" @click="submitForm">
        {{ submitting ? "提交中..." : "提交确认" }}
      </button>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>

      <section v-if="submitResult" class="result">
        <h2>提交结果</h2>
        <p><strong>记录ID：</strong>{{ submitResult.id }}</p>
        <p><strong>门店编码：</strong>{{ submitResult.storeCode }}</p>
        <p><strong>姓名：</strong>{{ submitResult.customerName }}</p>
        <p><strong>手机号：</strong>{{ submitResult.phone }}</p>
        <p><strong>提交时间：</strong>{{ submitResult.createdAt }}</p>
        <img :src="submitResult.signatureDataUrl" alt="签字图片" class="signature-image" />
      </section>
    </section>
  </main>
</template>
