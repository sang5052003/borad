<template>
  <md-table-card>
    <md-toolbar>
      <h1 class="md-title">{{msg}}</h1>
      <!--<md-button class="md-icon-button">-->
      <!--<md-icon>filter_list</md-icon>-->
      <!--</md-button>-->

      <md-button class="md-icon-button" @click="deleteClick">
        <md-icon>delete</md-icon>
      </md-button>

      <md-button class="md-icon-button">
        <md-icon>search</md-icon>
      </md-button>

      <write-form @refreshBoard="initList"></write-form>

    </md-toolbar>

    <md-table md-sort="id" md-sort-type="desc" @select="onSelect" @sort="onSort">
      <md-table-header>
        <md-table-row>
          <md-table-head md-sort-by="title" :md-numeric="false">Title</md-table-head>
          <md-table-head md-sort-by="contents" :md-numeric="false"
                         md-tooltip="The total amount of food energy and the given serving size">Contents
          </md-table-head>
          <md-table-head md-sort-by="id" :mdNumeric="false">ID</md-table-head>
        </md-table-row>
      </md-table-header>

      <md-table-body>
        <md-table-row v-for="(row, rowIndex) in posts" :key="rowIndex" :md-item="row" :md-auto-select="true"
                      :md-selection="true">
          <md-table-cell v-for="(column, columnIndex) in row" :key="columnIndex" :md-numeric="false"
                         v-if="columnIndex !== '_links'" @click.native="itemSelect(row._links)">
            {{ column }}
          </md-table-cell>

        </md-table-row>
      </md-table-body>
    </md-table>

    <md-table-pagination
      md-size="5"
      md-total="10"
      md-page="1"
      md-label="Rows"
      md-separator="of"
      :md-page-options="[5, 10, 25, 50]"
      @pagination="onPagination"></md-table-pagination>
  </md-table-card>
</template>

<script>
  import ChildComponent from './ChildComponent.vue'
  import MdTableCell from "../../node_modules/vue-material/src/components/mdTable/mdTableCell.vue";
  import MdButton from "../../node_modules/vue-material/src/components/mdButton/mdButton.vue";
  import WriteForm from "./WriteForm.vue"

  export default {
    name: 'Main',
    components: {
      MdButton,
      MdTableCell,
      ChildComponent,
      WriteForm
    },
    data: () => ({
      msg: "Board",
      board: [],
      posts: [],
      checked: null,
      checkList: [],
      postIds: []
    }),
    created: function () {
      var self = this
      self.initList()
    },
    methods: {
      onSelect: function (row) {
        var self = this
        self.checkList = row
        console.log(self.checkList)
      },
      onSort: function () {

      },
      onPagination: function () {

      },
      itemSelect: function (e) {
        var self = this
        var path = e.post.href
        path = path.charAt(path.length - 1)
        self.$router.push({
          path: 'post/id/' + path
//          path: self.$route.query.redirect
        })
      },
      deletePost: function (id) {
        var self = this
//        self.checkList
        var xhr = new XMLHttpRequest()
        xhr.open('DELETE', 'http://localhost:8080/post/' + id)
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          self.initList()
        }
        xhr.send()
      },
      deleteClick: function () { //여러번 통신
        //id valid
        var self = this
//        self.checkList

        for (var i = 0; i < self.checkList.length; i++){
          var path = self.checkList[i]._links.post.href
          path = path.charAt(path.length - 1)
          self.deletePost(path)
        }
      },
      initList: function () {
        var xhr = new XMLHttpRequest()

        var self = this

        xhr.open('GET', 'http://localhost:8080/post')
        xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
        xhr.onload = function () {
          self.posts = JSON.parse(xhr.responseText)._embedded.posts
        }
        xhr.send()
      }

    }
  };

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

  .btn {
    font-weight: normal;
    font-size: x-small;
    text-align: center;
  }

</style>
