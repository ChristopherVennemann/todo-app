<script lang="ts" setup>
import ItemBox from "@/components/ItemBox.vue";
import {Ref, ref, watch} from "vue";

let emit = defineEmits(['newMessage']);

let message: Ref<string> = ref('');
let isValidMessage: boolean = false;
let wasValidAttempt: boolean = true;

watch(message, () => {
  isValidMessage = (message.value.trim().length !== 0);
  setErrorMessage()
})

function sendMessage() {
  if (isValidMessage) {
    emit('newMessage', message.value);
    message.value = '';
    wasValidAttempt = true;
  } else {
    wasValidAttempt = false;
  }
  setErrorMessage()
}

function setErrorMessage() {
  if (!isValidMessage && !wasValidAttempt) {
    errorMessage.value = 'Enter some text to create a new To-Do!';
  } else {
    errorMessage.value = '';
  }
}

const errorMessage: Ref<string> = ref(' ');

</script>

<template>
  <ItemBox id="new-item">
    <input id="new-message" v-model="message" class="col"
           data-cy="textInput" placeholder=". . . add new item"
           type="text"
    />
    <img id="plus" alt="" class="col-3 align-self-center"
         src="@/images/plus_white.png"
         @click="sendMessage"
    />
  </ItemBox>
  <p>{{ errorMessage }}</p>
</template>

<style lang="scss" scoped>
@import "src/assets/fontsAndColors";

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

img {
  height: 60px;
  width: auto;
  opacity: 0.6;

  &:hover {
    opacity: 1 !important;
    cursor: pointer;
  }
}

p {
  height: 2em;
  font: $font-error;
  color: $error-color;
  opacity: 0.6;
}

</style>