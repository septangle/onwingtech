<template>
  <div>
    <div class="demo-upload-list" v-for="item in uploadList">
      <template v-if="item.status === 'finished'">
        <img :src="item.url">
        <div class="demo-upload-list-cover">
          <Icon type="ios-eye-outline" @click.native=""></Icon>
          <Icon type="ios-trash-outline" @click.native=""></Icon>
        </div>
      </template>
      <template v-else>
        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
      </template>
    </div>
    <Upload
      ref="upload"
      :format="['jpg','jpeg','png','gif']"
      :max-size="2048"
      :before-upload="handleUpload"
      multiple
      action="//jsonplaceholder.typicode.com/posts/">
      <Button type="ghost" icon="ios-cloud-upload-outline">选择你要上传的图片</Button>
    </Upload>
    <div v-if="file !== null">Upload file: {{ file.name }}
      <Button type="text" @click="upload" :loading="loadingStatus">
        {{ loadingStatus ? 'Uploading' : '上传图片' }}
      </Button>
    </div>
  </div>
</template>
<script>
    export default {
        data () {
            return {
                file: null,
                loadingStatus: false,
                uploadList:[]
            }
        },
        methods: {
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
          console.info(this.$refs.upload);
        }
    }
</script>

<style>
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
</style>