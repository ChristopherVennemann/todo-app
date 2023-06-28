<script lang="ts" setup>
import axios, {AxiosResponse, HttpStatusCode} from "axios";
import {onMounted, Ref, ref} from "vue";
import Item from "@/types/Item";

let items: Ref<Item[]> = ref([]);
let newItemMessage: string = '';

async function getData(): Promise<Item[]> {
  const response: AxiosResponse = await axios.get('http://localhost:8082/items');
  return response.data;
}

async function addNewItem(): Promise<void> {
  const response = await axios.post(
      'http://localhost:8082/items',
      {"message": newItemMessage}
  );

  if (response.status === HttpStatusCode.Created) {
    items.value.push(response.data);
    newItemMessage = '';
  }
}

async function deleteItem(id: number): Promise<void> {
  console.log(id)
  const response = await axios.delete(`http://localhost:8082/items/${id}`);
  if (response.status == HttpStatusCode.NoContent) {
    for (let i = 0; i < items.value.length; i++) {
      if (items.value[i].id == id) {
        items.value.splice(i, 1);
      }
    }
  }
}

onMounted(async () => {
  items.value = await getData()
})
</script>

<template>

  <div id="app-container">

    <p id="title">to-do :</p>

    <div>
      <div id="new-item" class="item-box row">
        <input id="new-message" v-model="newItemMessage" class="col" placeholder=". . . add new item" type="text"/>
        <img id="plus" alt="" class="col-3 align-self-center" src="@/images/plus_white.png" @click="addNewItem"/>
      </div>
    </div>

    <div id="item-list">
      <div v-for="item in items" :key="item.id" class="item-box row" data-cy="item">
        <p class="col align-self-end">{{ item.message }}</p>
        <img id="delete" alt="" class="col-2 align-self-center" src="@/images/trashcan.png"
             @click="deleteItem(item.id)"/>
        <img alt="" class="col-2 align-self-center" src="@/images/circle_empty_white.png"/>
      </div>

    </div>

  </div>
</template>


<style lang="scss" scoped>

$primary-color: #FFFFFF;
$shadow-color: #000000;
$font-stack: MyPoppins, Calibri, sans-serif;
$font-items: 1.6rem $font-stack;
$font-title: bold 7rem $font-stack;

html {
  font-size: 62.5%;
}

.item-box {
  height: 3em;
  padding: 0 1em;
  background-color: rgba($primary-color, 0.2);
  border-radius: 0.5em;
  font: $font-items;

  #delete {
    opacity: 0;
  }

  &:hover {
    box-shadow: 1px 1px rgba($shadow-color, 0.5);
    background-color: rgba($primary-color, 0.4);

    #delete {
      opacity: 0.6;
    }
  }

  img {
    height: 60px;
    width: auto;
    opacity: 0.6;

    &:hover {
      opacity: 0.8;
      cursor: pointer;
    }
  }
}

#app-container {
  padding-left: 15%;
  padding-right: 15%;
  padding-top: 50px;
  display: flex;
  flex-direction: column;
}

#title {
  font: $font-title;
  color: rgba($primary-color, 0.6);
}

#new-item {
  background-color: rgba($primary-color, 0.4);

  input {
    width: auto;
    background-color: rgba($primary-color, 0);
    border: none;

    &:focus {
      outline: none;

      &::placeholder {
        opacity: 0;
      }
    }
  }
}

#item-list {
  margin-top: 2em;
  display: flex;
  flex-direction: column;
  row-gap: 0.5em;

  p {
    padding-left: 0.5em;
  }
}

</style>