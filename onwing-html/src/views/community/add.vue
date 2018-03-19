<template>
  <div class="validateMain">
    <div class="validateView">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" class="formstyle">
        <FormItem label="小区名称" prop="communityName">
          <Input v-model="formValidate.communityName" placeholder="请填写小区名称"></Input>
        </FormItem>
        <FormItem label="小区地址" prop="communityAddress">
          <Input v-model="formValidate.communityAddress" placeholder="请填写小区地址"></Input>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="handleSubmit('formValidate')">添加</Button>
          <Button @click="handleReset('formValidate')">重填</Button>
        </FormItem>
      </Form>

    </div>
  </div>
</template>
<script>
  import GlobalServer from '../../config.js';
  import axios from 'axios';
  import Cookies from 'js-cookie';
  export default {
    name:'whitelist_add_index',
    data () {
      return {
        communityNameList:[],
        communityIDList:[],
        communityAddressList:[],
        cameraNumberList:[],
        controNumberList:[],
        formValidate: {
          communityName: '',
          communityAddress: '',
        },
        ruleValidate: {
          communityName: [
              { required: true, message: '请填写小区名称', trigger: 'blur' },
          ],
          communityAddress: [
              { required: true, message: '请填写小区地址', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init(){
        this.communityNameList = sessionStorage.getItem('communityName').split(',');
        this.communityIDList = sessionStorage.getItem('communityID').split(',');
        this.communityAddressList = sessionStorage.getItem('communityAddress').split(',');
        this.cameraNumberList = sessionStorage.getItem('cameraNumber').split(',');
        this.controNumberList = sessionStorage.getItem('controNumber').split(',');
      },
      handleError(error) {
        console.log('Error: ', error);
      },

      handleFileUp() {
        document.getElementById('household_photoup').click();
      },

      handleFilechange() {
        var file = this.$refs.photoup.files[0];
        var reader = new FileReader();
        let _this = this;
        this.file = file;
        reader.readAsDataURL(file);
        reader.onload = function(event){
          _this.photoUrl = event.target.result;
        };
      },

      handleSubmit(name) {
        let _this = this;
        let formdata = new FormData();
        let config = {
          headers:{
            'Content-Type':'application/x-www-form-urlencoded'
          }
        };
        _this.$refs[name].validate((valid) => {
          if(valid) {
            formdata.append('name',_this.formValidate.communityName);
            formdata.append('address',_this.formValidate.communityAddress);

            axios.post(GlobalServer.createCommunity,formdata,config)
              .then(function(response){
                let data = response.data;
                if(data.error === null) {
                  _this.communityNameList.push(_this.formValidate.communityName);
                  console.info(_this.communityNameList);
                  _this.communityIDList.push(data.newCommunityId);
                  _this.communityAddressList.push(_this.formValidate.communityAddress);
                  _this.cameraNumberList.push(0);
                  _this.controNumberList.push(0);
                  sessionStorage.setItem('communityName',_this.communityNameList);
                  sessionStorage.setItem('communityID',_this.communityIDList);
                  sessionStorage.setItem('communityAddress',_this.communityAddressList);
                  sessionStorage.setItem('cameraNumber',_this.cameraNumberList);
                  sessionStorage.setItem('controNumber',_this.cameraNumberList);
                  _this.$Message.success('添加成功！');
                  _this.handleReset('formValidate');
                  _this.householdDto = {};
                }
              })
              .catch(function(error){
                console.info(error);
              });
          }
        });
      },
      handleReset(name) {
        this.$refs[name].resetFields();
      }
    },
    created(){
      this.init();
    },
    mounted(){},
    activated(){}
  }
</script>
<style type="text/css" scoped>
  .validateMain {

  }
  .validateMain:before,
  .validateMain::after {
    content: '';
    display: table;
    clear: both;
  }

  .validateView {
    margin-left: 20px;
    float: left;
    width: 30%;
  }
</style>
