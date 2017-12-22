<template>
  <div>
    <form id="photoupForm" :action="photoupUrl" method="post" enctype="multipart/form-data" target="formSubmit">
        <input id="uploadfile-input" name="file" class="uploadfile-input" type="file" multiple accept="image/x-png, image/jpg, image/jpeg, image/gif" @change="fileChange($event)"/>
        <p id="uploadfile-button" @click="uploadClick()" class="uploadfile-button">
            <span>请选择要上传的文件</span>
        </p>
        <div class="submitWrap" @click="submitClick()">
            <span>提&nbsp;交</span>
            <input id="uploadfile-submit" type="submit" value="提 交" />
        </div>
    </form>
    <iframe id="iframe" name="formSubmit" style="display:none;"></iframe>
    <div>
      <p class="photoNumber" v-show="show"><span>本次上传照片的数量为：</span><span>{{photoNumber}}</span><span>张</span></p>
      <ul class="filelist">
        <li v-for="value in values">{{value.filename}}</li>
      </ul>
    </div>
  </div>
</template>
<script>
  import GlobalServer from '../../config.js';

  export default {
    data(){
      return {
        show: false,
        photoNumber:'',
        values:[],
        photoupUrl: GlobalServer.uploadPhotos
      }
    },
    methods: {
      uploadClick() {
        let EleInput = document.getElementById("uploadfile-input");

        EleInput.click();
      },
      submitClick(){
        let EleSubmit = document.getElementById("uploadfile-submit");
        let EleForm = document.getElementById("photoupForm");
        EleSubmit.click();
      },
      fileChange(event) {
        var files = event.target.files,
            length = files.length;
        
        if(length === 0) {
          return false;
        }

        this.photoNumber = length;
        this.values = [];
        this.show=true;
        for(var i=0; i<length; i++){
          var obj = {};
          var filename = files[i].name;
          obj = {filename:filename};
          this.values.push(obj);
        }
      }
    },
    mounted() {
      /* this.uploadClick(); */
      /* this.fileChange(); */
    }
  }
</script>

<style scoped>
    .demo-upload-list{
        display: inline-block;
        width: 60px;
        height: 60px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        margin-right: 4px;
    }
    .demo-upload-list img{
        width: 100%;
        height: 100%;
    }
    .demo-upload-list-cover{
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0,0,0,.6);
    }
    .demo-upload-list:hover .demo-upload-list-cover{
        display: block;
    }
    .demo-upload-list-cover i{
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        margin: 0 2px;
    }
    .uploadfile-input {
      display: none;
    }

    .uploadfile-button {
      padding: 8px 8px;
      line-height: 1;
      font-size: 14px;
      display: inline-block;
      border-radius: 3px;
      background-color: #2d8cf0;
      color: #fff;
      cursor: pointer;
      transition: none;
    }

    .uploadfile-button:hover {
      transition: opacity .5s;
      opacity: .9;
    }

    .uploadfile-button span {

    }

    .submitWrap {
      display: inline-block;
      cursor: pointer;
      line-height: 1;
      padding: 8px 15px;
      border-radius:3px;
      background-color: #ccc;
      font-size: 14px;
    }

    .submitWrap input[type=submit]{
      display: none;
    }

    .photoNumber {
      margin-top: 10px;
      font-size: 14px;
      line-height: 1;
    }

    .filelist {
      margin-top: 10px;
    }

    .filelist li {
      display: inline-block;
      padding: 5px 10px 10px 0;
      font-size: 14px;
      line-height: 1;
    }
</style>