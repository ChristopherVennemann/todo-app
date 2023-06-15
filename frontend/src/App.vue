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
  <div class="title-wrapper center-flex" id="app-title">
    <h1>TO-DO-LIST 2000</h1>
  </div>

  <div class="item-list-wrapper center-flex">
    <div class="item-list center-flex">
      <div class="item new-item center-flex">
        <input type="text" placeholder="new item..."/>
        <button>Add Item</button>
      </div>

      <div v-for="item in items" class="item item-highlight">
        <span class="item-text">{{ item.message }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>

#app-title {
  font-style: normal;
  text-decoration: underline;
  font-weight: bolder;
  font-size: larger;
  color: rgba(255,255,255,1);
  text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000;
}

.center-flex {
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: center;
  align-items: center;
}

.title-wrapper {
  flex-direction: row;
}

.item-list-wrapper {

}

.item-list {
  width: 80vw;
}

.item {
  margin-top: 1em;
  width: 80%;
  padding: 0.25em;
  border: 1px solid rgba(255,255,255,0);
  border-radius: 0.4em;
  -webkit-box-shadow: 2px 2px 6px 0 rgba(0,0,0,0.5);
  box-shadow: 2px 2px 6px 0 rgba(0,0,0,0.5);
  background-color: rgba(255,255,255,0.8);
}

.item-highlight:hover {
  background-color: rgba(255,255,255,0.7);
  border-color: white;
  cursor: pointer;
}

.new-item {
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 2em;
}

.item-text {
  margin-left: 0.5em;
}

input {
  width: 90%;
  background-color: rgba(255, 255, 255, 0.7);
  border: none;
  outline: none;
  border-bottom: 1px solid #ccc;
  border-radius: 0.2em;
  margin-left: 0.5em;

}

button {
  width: 20%;
  margin-left: 1em;
  margin-right: 0.5em;
}

button:hover {
  cursor: pointer;
}
</style>