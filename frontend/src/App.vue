<script setup lang="ts">
import axios, {AxiosResponse, HttpStatusCode} from "axios";
import {onMounted, Ref, ref} from "vue";
import Item from "@/types/Item";

let items: Ref<Item[]> = ref([])
let newItemMessage: String = ''

async function getData(): Promise<Item[]> {
  const response: AxiosResponse = await axios.get('http://localhost:8082/items')
  return response.data
}

async function addNewItem() {
  const response = await axios.post(
      'http://localhost:8082/items',
      {"message": newItemMessage}
  )

  if (response.status === HttpStatusCode.Created) {
    items.value.push(response.data);
    newItemMessage = ''
  }
}

onMounted(async () => {
  items.value = await getData()
})
</script>

<template>

  <div id="wrapper">

    <div class="vertical-center">
      <div id="my-header">
        <p>hier steht was</p>
        <p>hier auch</p>
      </div>
    </div>


    <div class="vertical-center">
      <div id="my-content">
        <p>hier steht was</p>
        <p>hier auch</p>
      </div>
    </div>


    <div class="vertical-center">
      <div id="my-footer">
        <p>hier steht was</p>
        <p>hier auch</p>
      </div>
    </div>

  </div>

</template>


<style scoped>

.vertical-center {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: center;
}

#wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  justify-content: space-between;
}


#my-header {
  width: 80%;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: space-between;
  align-items: baseline;


  font-family: Poppins, Calibri, sans-serif;
  font-style: normal;
  font-size: larger;
  color: rgba(255,255,255,0.8);
}

#my-footer {
  width: 80%;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: space-around;
  align-items: baseline;


  font-family: Poppins, Calibri, sans-serif;
  font-style: normal;
  font-size: larger;
  color: rgba(255,255,255,0.8);
}

</style>