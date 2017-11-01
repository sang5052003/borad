<template>
  <div>
  <md-dialog md-open-from="#fab" md-close-to="#fab" ref="dialog2">
    <md-dialog-title>Create new note</md-dialog-title>

    <md-dialog-content>
      <form>
        <md-input-container>
          <label>Note</label>
          <md-textarea></md-textarea>
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

        var jsonData = JSON.stringify({title:"new title", contents:"new con", authorId:"auid"})

//        console.log(jsonData)

        xhr.onload = function () {
          //refresh.
          self.$router.push({
            path: '/'
          })
          console.log('post onload')
        }
        xhr.send(jsonData)


      }
    }
  };
</script>

<style scoped>

</style>
