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
            <template v-for="item in reply">
              <md-input-container>
                <label>Reply</label>
                <md-textarea v-model="reply">
                  {{item}}
                </md-textarea>
                <!--<md-input v-model="post.contents" :readonly="readonly"></md-input>-->
              </md-input-container>
            </template>
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

  export default {
    components: {
      MdCardArea,
      MdCardActions,
      MdCardHeaderText,
      MdCardHeader,
      MdCard,
      MdLayout,
      MdButton
    },
    name: 'detailpost',
    data() {
      return {
        msg: '',
        post: {},
        readonly: true,
        editMode: true,
        reply: {}
      }
    },
    created: function () {
      var xhr = new XMLHttpRequest()

      var self = this
      var id = self.$route.params.id
      xhr.open('GET', 'http://localhost:8080/post/id/' + id)
      xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
      xhr.onload = function () {
        var post = JSON.parse(xhr.responseText)
        self.post = post
        self.reply = JSON.stringify(self.post.reply)
        console.log(self.post)
        console.log(self.reply)


      }
      xhr.send()
    },
    methods: {
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
