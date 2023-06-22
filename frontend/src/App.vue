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
        <p></p>
        <p></p>
      </div>
    </div>


    <div class="vertical-center">
      <div id="my-content">

        <p id="title">to-do :</p>

        <div>
          <div class="item-box row" id="new-item">
            <input class="col" type="text" v-model="newItemMessage" placeholder="new item..." id="new-message"/>
            <img class="col-3" @click="addNewItem" id="plus" src="@/images/plus_white.png" alt=""/>
          </div>
        </div>

        <div id="item-list">
          <div v-for="item in items" :key="item.id" class="item-box row" data-cy="item">
            <p class="col align-self-center" id="message">{{ item.message }}</p>
            <img class="col-2" src="@/images/circle_empty_white.png" alt="" id="checkmark">
          </div>
        </div>
      </div>


      <div class="vertical-center">
        <div id="my-footer">
          <p></p>
          <p></p>
        </div>
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
  align-content: flex-start;
}

.item-box {

  height: 6em;
  padding: 1em 1em;
  margin-top: 0.5em;
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 0.7em;

  font-family: MyPoppins, Calibri, sans-serif;
}

.item-box:hover {
  box-shadow: 1px 1px rgba(0,0,0,0.5);
  background-color: rgba(255,255,255,0.4);
}

#wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
}

#title {
  font-family: MyPoppins, Calibri, sans-serif;
  font-size: 5em;
  font-weight: bold;
  color: rgba(255,255,255,0.5);
}

#my-header {
  width: 80%;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: space-between;
  align-items: baseline;


  font-family: MyPoppins, Calibri, sans-serif;
  font-style: normal;
  font-size: larger;
  color: rgba(255,255,255,0.8);
}

#my-content {
  width: 60%;
}

#new-item {
  background-color: rgba(255,255,255,0.4);
}

#new-message {
  width: auto;
  font-size: larger;
  background-color: rgba(255,255,255,0);
  border: none;
}

#new-message:focus {
  outline: none;
}

#item-list {
  margin-top: 2em;
}

#plus {
  height: 70px;
  width: auto;
  opacity: 0.6;
}

#plus:hover {
  opacity: 0.9;
}


#message {
  font-size: larger;
  padding-left: 1em;
}

#checkmark {
  height: 70px;
  width: auto;
  opacity: 0.5;
}

#checkmark:hover {
  opacity: 0.8;
}

#my-footer {
  width: 80%;
  display: flex;
  position: absolute;
  bottom: 0;
  flex-direction: row;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: space-around;
  align-items: baseline;


  font-family: MyPoppins, Calibri, sans-serif;
  font-style: normal;
  font-size: larger;
  color: rgba(255,255,255,0.8);
}

</style>