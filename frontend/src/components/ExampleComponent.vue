<script lang="ts" setup>

const emit = defineEmits(['greeting']);

function greet() {
  emit('greeting', 'hello!')
}
</script>

<template>
  <button @click="greet">Click Me!</button>
</template>