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

  <div id="my-header">
    <p>hier steht was</p>
    <p>hier auch</p>
  </div>

  <div class="center-flex">
    <div class="center-flex" id="content">

      <div id="app-title">
        <h1>to-do :</h1>
      </div>


      <div class="item new-item">
        <div class="center-flex">
          <input type="text" v-model="newItemMessage" placeholder="new item..."/>
          <button @click="addNewItem">Add Item</button>
        </div>
      </div>

      <div class="item-list center-flex">
        <div v-for="item in items" :key="item.id" class="item item-highlight" data-cy="item">
          <span class="item-text">{{ item.message }}</span>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>

#my-header {
  display: flex;
  font-family: Poppins;
  font-style: normal;
  font-size: larger;
  color: rgba(255,255,255,0.8);
  flex-direction: row;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: space-between;
  align-items: baseline;
}

#content {
  width: 70%
}

#app-title {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  font-family: Poppins;
  font-style: normal;
  font-size: larger;
  color: rgba(255,255,255,0.8);
}

.center-flex {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: center;
  align-items: center;
}

.item-list {
  width: 80vw;
}

.item {
  margin-top: 1em;
  width: 80%;
  padding: 0.25em;
  border: 1px solid rgba(255,255,255,0);
  border-radius: 1em;
  background-color: rgba(255,255,255,0.15);
}

.item-highlight:hover {
  background-color: rgba(255,255,255,0.3);
//border-color: rgba(255,255,255,0.5);
  -webkit-box-shadow: 2px 2px 6px 0 rgba(0,0,0,0.5);
  box-shadow: 1px 1px 2px 0 rgba(0,0,0,0.5);
  cursor: pointer;
}

.new-item {
  width: 80vw;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 2em;
}

.item-text {
  margin-left: 0.5em;
}

input {
  width: 90%;
  background-color: rgba(255, 255, 255, 0.0);
  border: none;
  outline: none;
  margin-left: 0.5em;
}

input:focus {
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 1em;
}

button {
  width: fit-content;
  white-space: nowrap;
  margin-left: 1em;
  margin-right: 0.5em;
}

button:hover {
  cursor: pointer;
}
</style>