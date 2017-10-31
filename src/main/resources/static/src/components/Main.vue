<template>
    <md-table-card>
      <md-toolbar>
        <h1 class="md-title">Nutrition</h1>
        <md-button class="md-icon-button">
          <md-icon>filter_list</md-icon>
        </md-button>

        <md-button class="md-icon-button">
          <md-icon>search</md-icon>
        </md-button>
      </md-toolbar>

      <md-table md-sort="id" md-sort-type="desc" @select="onSelect" @sort="onSort">
        <md-table-header>
          <md-table-row>
            <md-table-head md-sort-by="title" :md-numeric="false">Title</md-table-head>
            <md-table-head md-sort-by="contents" :md-numeric="false" md-tooltip="The total amount of food energy and the given serving size">Contents</md-table-head>
            <md-table-head md-sort-by="id" :mdNumeric="false">ID</md-table-head>
          </md-table-row>
        </md-table-header>

        <md-table-body>
          <md-table-row v-for="(row, rowIndex) in posts" :key="rowIndex" :md-item="row" :md-auto-select="true" :md-selection="false" >
            <md-table-cell v-for="(column, columnIndex) in row" :key="columnIndex" :md-numeric="false" v-if="columnIndex !== '_links'" @click.native="itemSelect(row._links)">
              {{ column }}
            </md-table-cell>
            <md-table-cell><md-button class="md-primary">Primary</md-button></md-table-cell>
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

export default {
    name: 'Main',
    components: {
      MdTableCell,
      ChildComponent
    },
    data: () => ({
      msg: "paMsg",
      board: [],
      posts: []
    }),
    created: function () {
      var xhr = new XMLHttpRequest()

      var self = this

      xhr.open('GET', 'http://localhost:8080/post')
      xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8')
      xhr.onload = function () {
        self.posts = JSON.parse(xhr.responseText)._embedded.posts
      }
      xhr.send()

    },
    methods: {
      onSelect: function () {
        console.log('sel')
      },
      onSort: function () {

      },
      onPagination: function () {

      },
      itemSelect: function (e) {
        console.log(e)
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

md-table-head {
  text-align: center;
}
</style>
