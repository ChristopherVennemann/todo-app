<script lang="ts" setup>
import axios, {AxiosResponse, HttpStatusCode} from "axios";
import {onMounted, Ref, ref} from "vue";
import {
  CollectionModel,
  Item,
  LinkCollection
} from "@/types/CollectionModelTypes";
import ItemList from "@/components/ItemList.vue";
import NewItem from "@/components/NewItem.vue";
import CategorySelector from "@/components/CategorySelector.vue";

const hateoasUrl: string = 'http://localhost:8082/hateoas'

let hateoasModel: CollectionModel;
let itemModel: CollectionModel;
let itemEndpoints: LinkCollection;

let allItems: Item[] = [];
const visibleItems: Ref<Item[]> = ref([]);
let itemSelection: string = 'all';

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
    allItems.push(response.data);
    setVisibleItems()
    visibleItems.value.sort(sortByDoneAndId);
  }
}

async function deleteItem(item: Item): Promise<void> {
  const response: AxiosResponse = await axios.delete(item._links.delete.href);
  if (response.status == HttpStatusCode.NoContent) {
    for (let i = 0; i < allItems.length; i++) {
      if (allItems[i].id == item.id) {
        allItems.splice(i, 1);
      }
    }
    setVisibleItems()
    visibleItems.value.sort(sortByDoneAndId);
  }
}

async function setDoneStatus(url: string): Promise<void> {
  const response: AxiosResponse = await axios.put(url);
  if (response.status !== HttpStatusCode.Ok) {
    return;
  }
  const updatedItem: Item = response.data;
  for (let i = 0; i < allItems.length; i++) {
    if (allItems[i].id == updatedItem.id) {
      allItems[i] = updatedItem;
    }
    setVisibleItems()
    visibleItems.value.sort(sortByDoneAndId);
  }
}

function setItemSelection(category: string) {
  itemSelection = category;

  setVisibleItems()
  visibleItems.value.sort(sortByDoneAndId);
}

function setVisibleItems() {
  switch (itemSelection) {
    case 'all':
      visibleItems.value = [];
      visibleItems.value.push(...allItems);
      break;
    case 'unfinished':
      visibleItems.value = [];
      for (const item of allItems) {
        if (!item.isDone) {
          visibleItems.value.push(item);
        }
      }
      break;
    case 'finished':
      visibleItems.value = [];
      for (const item of allItems) {
        if (item.isDone) {
          visibleItems.value.push(item);
        }
      }
  }
}

onMounted(async () => {
  hateoasModel = await getHateoasModel(hateoasUrl);
  itemModel = await getItems(hateoasModel._links.itemCollection.href);
  itemEndpoints = itemModel._links;

  allItems = itemModel?._embedded ? itemModel._embedded.itemResponseList : [];
  setVisibleItems()
  visibleItems.value.sort(sortByDoneAndId);
})
</script>

<template>
  <div id="app-container">

    <p id="title">to-do :</p>


    <CategorySelector
        @selectedCategory="setItemSelection"
    />

    <NewItem
        @newMessage="addNewItem"
    />

    <ItemList
        :items=visibleItems
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