<template>
  <div class="validateMain">
    <div class="validateView">
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" class="formstyle">
        <FormItem label="用户名" prop="userName">
          <Input v-model="formValidate.userName" placeholder="请填写用户名"></Input>
        </FormItem>
        <FormItem label="登录密码" prop="password">
          <Input v-model="formValidate.password" placeholder="请填写登录密码"></Input>
        </FormItem>
        <FormItem label="手机号" prop="tel">
          <Input v-model="formValidate.tel" placeholder="请填写手机"></Input>
        </FormItem>
        <FormItem label="所属小区" prop="estateName">
          <Select v-model="formValidate.estateName">
            <Option v-for="item in estateList" :value="item.value" :key="item.value">{{item.label}}</Option>
          </Select>
        </FormItem>
        <FormItem label="类型" prop="userType">
          <RadioGroup v-model="formValidate.userType">
            <Radio label="物业管理员">物业管理员</Radio>
            <Radio label="物业保安">物业保安</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="handleSubmit('formValidate')">添加</Button>
          <Button @click="handleReset('formValidate')" style="margin-left: 10px;">重填</Button>
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
    name:'user_add_index',
    data () {
      return {
        formValidate: {
          userName: '',
          password: '',
          tel: '',
          estateName: '',
          userType: ''
        },
        file:'',
        ruleValidate: {
          userName: [
            { required: true, message: '请填写用户名', trigger: 'blur' },
            { type: 'string', pattern: /[0-9a-zA-Z]{4,8}/g, message: '用户名格式错误', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请填写登录密码', trigger: 'blur' },
            { type: 'string', message: '密码格式错误或长度不够，密码中仅可包含数字，英文字母，!，-，_', trigger: 'blur' }
          ],
          tel: [
            { required: true, message: '请填写联系电话', trigger: 'blur' },
            { type: 'string', pattern:/^\d{7,12}$/g, message: '联系电话格式错误', trigger: 'blur' }
          ],
          estateName: [
            { required: true, message: '请选择所属小区', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '请选择账号类型', trigger: 'change' }
          ]
        }
      }
    },
    methods: {
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
            formdata.append('userName',_this.formValidate.userName);
            formdata.append('password',_this.formValidate.password);
            formdata.append('tel',_this.formValidate.tel);
            formdata.append('estateName',_this.formValidate.estateName);
            formdata.append('userType',_this.formValidate.userType);
            axios.post(GlobalServer.addHouseHold,formdata,config)
              .then(function(response){
                let data = response.data;
                if(data.error === null) {
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
    created(){},
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
