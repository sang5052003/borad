<template>
  <div>
  <md-dialog md-open-from="#fab" md-close-to="#fab" ref="dialog2">
    <md-dialog-title>Create new Reply</md-dialog-title>

    <md-dialog-content>
      <form>
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

  <md-button class="md-fab" id="fab" @click.native="openDialog('dialog2')">
    <md-icon>message</md-icon>
  </md-button>
  </div>
</template>

<script>
  export default {
    name: 'writeform',
    data: () => ({
//      form: {title: String, contents: String}
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
        xhr.open('POST', "http://localhost:8080/post/write/reply");
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");

        var id = self.$route.params.id
        var jsonData = JSON.stringify({id: id, reply: self.contents})

//        console.log(jsonData)

        xhr.onload = function () {
          //refresh.
//          self.$router.push({
//            path: '/'
//          })
          self.$emit('refreshDetailBoard')
        }
        xhr.send(jsonData)

        this.closeDialog(ref)
      }
    }
  };
</script>

<style scoped>

</style>
