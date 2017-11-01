<template>
  <div class="detailPost">
    <h3>{{ msg }}</h3>
    <ul>
      <li v-for="item in post">{{item}}</li>
      <md-button @click="toList">목록으로</md-button>
    </ul>
  </div>
</template>
<script>
  import MdButton from "../../node_modules/vue-material/src/components/mdButton/mdButton.vue";

  export default {
    components: {MdButton},
    name: 'detailpost',
    data () {
      return {
        msg: 'detailpostMSG',
        post: null
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
        console.log("post")
        console.log(post)
        self.post = post
      }
      xhr.send()
    },
    methods: {
      toList: function () {
        var self = this
        self.$router.push({
          path: "/"
        })
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
