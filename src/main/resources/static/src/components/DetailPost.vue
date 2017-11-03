<template>
  <div class="detailPost">
    <h1 class="">{{msg}}</h1>
    <md-card>
      <md-card-actions>
        <md-button class="md-fab" @click="toList">
          <md-icon>list</md-icon>
        </md-button>
        <template>
          <md-button md-theme="green" class="md-fab" v-if="editMode" @click="edit">
            <md-icon>edit</md-icon>
          </md-button>
          <md-button md-theme="green" class="md-fab" v-else="editMode" @click="submit">
            <md-icon>save</md-icon>
          </md-button>
        </template>
      </md-card-actions>

      <form novalidate @submit.stop.prevent="submit">
        <md-card-header>
          <md-card-header-text>
            <md-input-container>
              <label>Title</label>
              <md-input v-model="post.title" :readonly="readonly"></md-input>
            </md-input-container>
          </md-card-header-text>
        </md-card-header>

        <md-card-content>
          <md-input-container>
            <label>Contents</label>
            <md-textarea v-model="post.contents" :readonly="readonly"></md-textarea>
            <!--<md-input v-model="post.contents" :readonly="readonly"></md-input>-->
          </md-input-container>
        </md-card-content>

        <md-card-area>
          <md-card-content>
            <template v-for="(item, index) in reply">
              <md-input-container>
                <!--<label>Reply</label>-->
                <!-- save, watch?? -->
                <md-textarea v-model="reply[index].v" :readonly="reply[index].edit">
                </md-textarea>

                <template>
                  <md-button class="md-icon-button" v-if="reply[index].edit" @click="editReply(index)">
                    <md-icon>edit</md-icon>
                  </md-button>
                  <md-button class="md-icon-button" v-else="reply[index].edit" @click="submitReply(index)">
                    <md-icon>save</md-icon>
                  </md-button>
                </template>
                <md-button class="md-icon-button" @click="deleteReply(index)">
                  <md-icon>delete</md-icon>
                </md-button>

              </md-input-container>
            </template>

            <write-form-reply @refreshDetailBoard="initReply"></write-form-reply>

          </md-card-content>
        </md-card-area>

      </form>

    </md-card>


  </div>
</template>
<script>
  import MdButton from "../../node_modules/vue-material/src/components/mdButton/mdButton.vue";
  import MdLayout from "../../node_modules/vue-material/src/components/mdLayout/mdLayout.vue";
  import MdCard from "../../node_modules/vue-material/src/components/mdCard/mdCard.vue";
  import MdCardHeader from "../../node_modules/vue-material/src/components/mdCard/mdCardHeader.vue";
  import MdCardHeaderText from "../../node_modules/vue-material/src/components/mdCard/mdCardHeaderText.vue";
  import MdCardActions from "../../node_modules/vue-material/src/components/mdCard/mdCardActions.vue";
  import MdCardArea from "../../node_modules/vue-material/src/components/mdCard/mdCardArea.vue";
  import WriteFormReply from "./WriteFormReply.vue"

  export default {
    components: {
      MdCardArea,
      MdCardActions,
      MdCardHeaderText,
      MdCardHeader,
      MdCard,
      MdLayout,
      MdButton,
      WriteFormReply
    },
    name: 'detailpost',
    data() {
      return {
        msg: '',
        post: {},
        readonly: true,
        editMode: true,
        reply: []
      }
    },
    created: function () {
      var self = this
      self.initDetailPost()
    },
    methods: {
      initDetailPost: function () {
        var xhr = new XMLHttpRequest()

        var self = this
        var id = self.$route.params.id
        xhr.open('GET', 'http://localhost:8080/post/id/' + id)
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          var post = JSON.parse(xhr.responseText)
          self.post = post

          for (var i = 0; i < self.post.reply.length; i++){
            self.reply.push({"v": self.post.reply[i], "edit": true})
          }
        }
        xhr.send()
      },
      initReply: function () {
        var xhr = new XMLHttpRequest()

        var self = this
        var id = self.$route.params.id
        xhr.open('GET', 'http://localhost:8080/post/id/' + id)
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          var post = JSON.parse(xhr.responseText)
          self.post = post

          self.reply.push({"v": self.post.reply[self.post.reply.length - 1], "edit": true})
        }
        xhr.send()
      },
      initReplyDelete: function (idx) {
        var xhr = new XMLHttpRequest()

        var self = this
        var id = self.$route.params.id
        xhr.open('GET', 'http://localhost:8080/post/id/' + id)
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          var post = JSON.parse(xhr.responseText)
          self.post = post

          self.reply.splice(idx, 1) //idx위치의 1개 삭제
        }
        xhr.send()
      }
      ,
      toList: function () {
        var self = this
        self.$router.push({
          path: "/"
        })
      },
      submit: function () {
        var xhr = new XMLHttpRequest()

        var self = this
        var id = self.$route.params.id
        xhr.open('PUT', 'http://localhost:8080/post/put')
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')

        var jsonData = JSON.stringify(self.post)

        console.log(jsonData)

        xhr.onload = function () {
          var post = JSON.parse(xhr.responseText)
          self.post = post
        }
        xhr.send(jsonData)

        self.readonly = true
        self.editMode = true
      },
      edit: function () {
        var self = this
        self.readonly = false
        self.editMode = false
      },
      toObj: function (arr) {
        var rv = {};
        for (var i = 0; i < arr.length; ++i)
          rv[i] = arr[i]
        return rv
      },
      editReply: function (index) {

        var self = this
        self.reply[index].edit = false
      },
      submitReply: function (index) {

        var self = this
        self.reply[index].edit = true
//        self.post.reply = self.reply //기존의 post.reply를 이 vue에서 변경한 reply배열 객체로 변경해 버린다
        //front 처리
        for(var i= 0; i< self.reply.length; i++){
          self.post.reply[i] = self.reply[i].v
        }

        self.submit();
      },
      deleteReply: function (index) {
        var self = this
        var xhr = new XMLHttpRequest()

        var id = self.$route.params.id
        var jsonData = JSON.stringify({id: id, replyIdx: index})

        xhr.open('DELETE', 'http://localhost:8080/post/reply/delete')
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          self.initReplyDelete(index)
        }
        xhr.send(jsonData)
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
