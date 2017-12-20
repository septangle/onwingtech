<template>
  <div>
    <form :action="photoupUrl" method="post" enctype="multiple/form-data">
        <input id="uploadfile-input" name="uploadfile" class="uploadfile-input" type="file" multiple accept="image/x-png, image/jpg, image/jpeg, image/gif"/>
        <p id="uploadfile-button" @click="uploadClick()" class="uploadfile-button">
          <span>请选择要上传的文件</span>
        </p>
        <div class="submitWrap" @click="submitClick()">
          <span>提&nbsp;交</span>
          <input id="uploadfile-submit" type="submit" value="提 交" />
        </div>
    </form>
  </div>
</template>
<script>
  import GlobalServer from '../../config.js';
  export default {
    data(){
      return {
        photoupUrl: GlobalServer.photoUp
      }
    },
    methods: {
      uploadClick() {
        let EleInput = document.getElementById("uploadfile-input");

        EleInput.click();
      },
      submitClick(){
        let EleSubmit = document.getElementById("uploadfile-submit");
        EleSubmit.click();
      },
      fileChange() {
        var EleInput = document.getElementById("uploadfile-input");
        EleInput.addEventListener('change',function(source){
          var fileList = source.target.files,
              fileLength = fileList.length,
              gallaryEle = document.getElementById('photoGallary');
          var imgEle = document.createElement('img');
          if(window.FileReader) {
            for(var i=0;i<fileLength;i++){
              var fr = new FileReader();
              fr.readAsDataURL(fileList[i]);
              fr.onload = function(e) {
                imgEle.id = 'id' + i;
                imgEle.src = e.target.result;
              };
              gallaryEle.appendChild(imgEle);
            }
          } else {
            console.info('浏览器不支持FileReader');
          }
        })
      }
    },
    mounted() {
      /* this.uploadClick(); */
      /* this.fileChange(); */
    }
  }
    /* export default {
        data () {
            return {
                file: null,
                loadingStatus: false,
                uploadList:[]
            }
        },
        methods: {
            photoView() {
              console.info('mounted');
              let inputEle = document.getElementById('uploadfile-input');
              inputEle.addEventListener('change',function(){
                console.info('change');
                console.info(this);
              })
            },
            handleUpload (file,event) {
                console.info(file);
                this.file = file;
                console.info(event);
                return false;
            },
            upload () {
                this.loadingStatus = true;
                setTimeout(() => {
                    this.file = null;
                    this.loadingStatus = false;
                    this.$Message.success('Success')
                }, 1500);
            }
        },
        mounted () {
          console.info('mounted_2');
          photoView();
          console.info(this.$refs.upload);
        }
    } */
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

    .photoGallary{
      margin: 15px 0;
      width: 800px;
      display: inline-block;
    }
    .photoGallary p {
      float: left;
      position: relative;
      padding: 5px;
      width: 100px;
    }
    .photoGallary .removePhoto {
      position: absolute;
      right: 0;
      top: 0;
      width: 10px;
      height: 10px;
      background-color: #f00;
      border-radius: 50%;
      transform: rotate(45deg);
      cursor: pointer;
      z-index: 99;
    }
    .photoGallary .removePhoto:before {
      content: "";
      position: absolute;
      left: 5%;
      top:4px;
      background-color: #fff;
      width: 80%;
      height: 2px;
    }
    .photoGallary .removePhoto:after{
      content: "";
      position: absolute;
      left: 4px;
      top:5%;
      background-color: #fff;
      width: 2px;
      height: 80%;
    }
    .photoGallary img {
      width: 100%;
    }
</style>