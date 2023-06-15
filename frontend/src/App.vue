<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";

let items = ref([])

async function getData() {
  const response = await axios.get('http://localhost:8082/items')
  return response.data
}

onMounted(async () => {
  items.value = await getData()
})
</script>

<template>
  <h3>TO-DO-LIST 2000</h3>
  <ul>
    <li v-for="item in items">{{ item.message }}</li>
  </ul>
</template>