<script setup>
import {inject} from 'vue'

const axios = inject('axios')

const getList = () => {
  axios.get('http://localhost:8082/items')
      .then((response) => {
        console.log(response.data)
      })
}
axios
</script>

<template>
  <p>Hello frontend</p>
  <button @click="getList">get backend</button>
</template>