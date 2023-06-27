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
            <input class="col" type="text" v-model="newItemMessage" placeholder=". . . add new item" id="new-message"/>
            <img class="col-3 align-self-center" @click="addNewItem" src="@/images/plus_white.png" alt="" id="plus"/>
          </div>
        </div>

        <div id="item-list">
          <div v-for="item in items" :key="item.id" class="item-box row" data-cy="item">
            <p class="col align-self-end">{{ item.message }}</p>
            <img class="col-2 align-self-center" src="@/images/circle_empty_white.png" alt="">
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
$font-items: 1.6rem $font-stack;
$font-title: bold 7rem $font-stack;

html {
  font-size: 62.5%;
}

.vertical-center {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  justify-content: center;
  align-content: flex-start;
}

.item-box {
  height: 3em;
  padding: 0em 1em;
  margin-top: 0.5em;
  background-color: rgba($primary-color, 0.2);
  border-radius: 0.5em;
  font: $font-items;
  &:hover {
    box-shadow: 1px 1px rgba($shadow-color,0.5);
    background-color: rgba($primary-color,0.4);
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

#center-wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  flex-wrap: nowrap;
}

#title {
  font: $font-title;
  color: rgba($primary-color,0.6);
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
  p {
    padding-left: 0.5em;
  }
}

</style>