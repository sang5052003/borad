<template>
  <div>
  <md-dialog md-open-from="#fab" md-close-to="#fab" ref="dialog2">
    <md-dialog-title>Create new write</md-dialog-title>

    <md-dialog-content>
      <form>
        <md-input-container>
          <label>title</label>
          <md-input v-model="title"></md-input>
        </md-input-container>
        <md-input-container>
          <label>contents</label>
          <md-textarea v-model="contents"></md-textarea>
        </md-input-container>
      </form>
    </md-dialog-content>

    <md-dialog-actions>
      <md-button class="md-primary" @click.native="closeDialog('dialog2')">Cancel</md-button>
      <md-button class="md-primary" @click.native="writeDialog('dialog2')">Create</md-button>
    </md-dialog-actions>
  </md-dialog>

  <md-button class="md-icon-button" id="fab" @click.native="openDialog('dialog2')">
    <md-icon>edit</md-icon>
  </md-button>
  </div>
</template>

<script>
  export default {
    name: 'writeform',
    data: () => ({
//      form: {title: String, contents: String}
      title: '',
      contents: ''
    }),
    methods: {
      openDialog(ref) {
        this.$refs[ref].open();
      },
      closeDialog(ref) {
        this.$refs[ref].close();
      },
      onOpen() {
        console.log('Opened');
      },
      onClose(type) {
        console.log('Closed', type);
      },
      writeDialog(ref) {
        var xhr = new XMLHttpRequest()
        var self = this
        xhr.open('POST', "http://localhost:8080/post/write");
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");

        console.log('check')
        console.log(self.title)
        console.log(self.contents)

        var jsonData = JSON.stringify({title: self.title, contents: self.contents, authorId: "auid"})

//        console.log(jsonData)

        xhr.onload = function () {
          //refresh.
//          self.$router.push({
//            path: '/'
//          })
          self.$emit('refreshBoard')
          console.log('post onload')
        }
        xhr.send(jsonData)

        this.closeDialog(ref)
      }
    }
  };
</script>

<style scoped>

</style>
