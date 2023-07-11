<script lang="ts" setup>
import axios, {AxiosResponse, HttpStatusCode} from "axios";
import {onMounted, Ref, ref} from "vue";
import {CollectionModel, Item, LinkCollection} from "@/types/CollectionModelTypes";
import ItemList from "@/components/ItemList.vue";
import NewItem from "@/components/newItem.vue";

const hateoasUrl: string = 'http://localhost:8082/hateoas'

let hateoasModel: CollectionModel;
let itemModel: CollectionModel;
let itemEndpoints: LinkCollection;

const items: Ref<Item[]> = ref([]);

// alle axios requests in service.ts auslagern

async function getHateoasModel(url: string): Promise<CollectionModel> {
  const response: AxiosResponse = await axios.get(url);
  return response.data;
}

async function getItems(url: string): Promise<CollectionModel> {
  const response: AxiosResponse = await axios.get(url);
  return response.data;
}

function sortByDoneAndId(a: Item, b: Item): number {
  if (a.isDone === b.isDone) {
    return a.id - b.id;
  }
  if (a.isDone === true) {
    return 1;
  }
  return -1;
}

async function addNewItem(message): Promise<void> {
  const response: AxiosResponse = await axios.post(
      itemEndpoints.post.href,
      {"message": message}
  );
  if (response.status === HttpStatusCode.Created) {
    items.value.push(response.data);
  }
}

async function deleteItem(item: Item): Promise<void> {
  const response: AxiosResponse = await axios.delete(item._links.delete.href);
  if (response.status == HttpStatusCode.NoContent) {
    for (let i = 0; i < items.value.length; i++) {
      if (items.value[i].id == item.id) {
        items.value.splice(i, 1);
      }
    }
    items.value.sort(sortByDoneAndId);
  }
}

async function setDoneStatus(url: string): Promise<void> {
  const response: AxiosResponse = await axios.put(url);
  if (response.status !== HttpStatusCode.Ok) {
    return;
  }
  const updatedItem: Item = response.data;
  for (let i = 0; i < items.value.length; i++) {
    if (items.value[i].id == updatedItem.id) {
      items.value[i] = updatedItem;
    }
    items.value.sort(sortByDoneAndId);
  }
}

onMounted(async () => {
  hateoasModel = await getHateoasModel(hateoasUrl);
  itemModel = await getItems(hateoasModel._links.itemCollection.href);

  items.value = itemModel?._embedded ? itemModel._embedded.itemResponseList : [];
  itemEndpoints = itemModel._links;
})

</script>

<template>
  <div id="app-container">

    <p id="title">to-do :</p>

    <NewItem
        @newMessage="addNewItem"
    />

    <ItemList
        :items=items
        @delete="deleteItem"
        @setDoneStatus="setDoneStatus"
    />

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

</style>