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

  <div id="center-wrapper">

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
            <input class="col" type="text" v-model="newItemMessage" placeholder="new item..."/>
            <img class="col-3" @click="addNewItem" src="@/images/plus_white.png" alt=""/>
          </div>
        </div>

        <div id="item-list">
          <div v-for="item in items" :key="item.id" class="item-box row" data-cy="item">
            <p class="col align-self-center">{{ item.message }}</p>
            <img class="col-2" src="@/images/circle_empty_white.png" alt="">
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


<style scoped lang="scss">

$primary-color: #FFFFFF;
$shadow-color: #000000;
$font-stack: MyPoppins, Calibri, sans-serif;
$font-items: 6rem, $font-stack;

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
  background-color: rgba($primary-color, 0.2);
  border-radius: 0.7em;

  font: $font-items;
}

.item-box:hover {
  box-shadow: 1px 1px rgba($shadow-color,0.5);
  background-color: rgba($primary-color,0.4);
}

#center-wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
}

#title {
  font-family: $font-stack;
  font-weight: bold;
  color: rgba($primary-color,0.5);
}

#my-header {
  width: 80%;
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-content: center;
  justify-content: space-between;
  align-items: baseline;

  font-family: $font-stack;
  color: rgba($primary-color,0.8);
}

#my-content {
  width: 60%;
}

#new-item {
  background-color: rgba($primary-color,0.4);

  input {
    width: auto;
    background-color: rgba($primary-color,0);
    border: none;
  }
  input:focus {
    outline: none;
  }

  img {
    height: 70px;
    width: auto;
    opacity: 0.6;
  }
  img:hover {
    opacity: 0.9;
  }
}

#item-list {
  margin-top: 2em;
  p {
    padding-left: 1em;
  }

  img {
    height: 70px;
    width: auto;
    opacity: 0.5;
  }
  img:hover {
    opacity: 0.8;
  }
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

  font-family: $font-stack;
  color: rgba($primary-color,0.8);
}

</style>