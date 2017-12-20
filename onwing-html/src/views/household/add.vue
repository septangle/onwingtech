<template>
    <div class="validateMain">
        <!-- <div class="photoView">
            <template>
                <div class="editWrap" style="">
                    <Icon size="24" type="edit" @click.native="handleFileUp()"></Icon>
                    <Icon size="30" type="trash-b" @click.native="handlePhotoRemove()"></Icon>
                </div>
                <img :src="photoUrl" ref="img">
            </template>
        </div> -->

        <div class="validateView">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" class="formstyle">
                <FormItem label="门禁卡号" prop="cardNumber">
                    <Input v-model="formValidate.cardNumber" placeholder="请输入门禁卡号"></Input>
                </FormItem>
                <FormItem label="住户姓名" prop="householdName">
                    <Input v-model="formValidate.householdName" placeholder="请输入住户姓名"></Input>
                </FormItem>
                <FormItem label="联系电话" prop="tel">
                    <Input v-model="formValidate.tel" placeholder="请输入联系电话"></Input>
                </FormItem>
                <FormItem label="性别" prop="gender">
                    <RadioGroup v-model="formValidate.gender">
                        <Radio label="男">男</Radio>
                        <Radio label="女">女</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="楼号/单元号" prop="buildingBlockNumber">
                  <Input v-model="formValidate.buildingBlockNumber"></Input>
                </FormItem>
                <FormItem label="房号" prop="roomNumber">
                  <Input v-model="formValidate.roomNumber"></Input>
                </FormItem>
                <FormItem label="备注" prop="remarks">
                    <Input v-model="formValidate.remarks" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="备注信息不要超过200字"></Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" @click="handleSubmit('formValidate')">添加</Button>
                    <Button @click="handleReset('formValidate')">重填</Button>
                </FormItem>
            </Form>
            <form :action="baseUrl" method="post" type="" style="display:none">
              <input id="household_photoup" type="file" name="photoUrl" @change="handleFilechange()" ref="photoup">
              <!-- <input type="text" name="cardNumber" :value="formValidate.cardNumber">
              <input type="text" name="householdName" :value="formValidate.householdName">
              <input type="text" name="tel" :value="formValidate.tel">
              <input type="text" name="gender" :value="formValidate.gender">
              <input type="text" name="buildingBlockNumber" :value="formValidate.buildingBlockNumber">
              <input type="text" name="roomNumber" :value="formValidate.roomNumber">
              <textarea type="textarea" name="remarks" :value="formValidate.remarks"></textarea>
              <input type="submit" ref="addSubmit"> -->

            </form>
        </div>
    </div>
</template>
<script>
    import GlobalServer from '../../config.js';
    import axios from 'axios';

    export default {
        name:'household_add',
        data () {
            return {
                photoUrl:"/images/avatars-man.png",
                baseUrl:GlobalServer.addHouseHold,
                dto: {
                    householdDto:{}
                },
                formValidate: {
                    cardNumber: '',
                    householdName: '',
                    tel: '',
                    gender: '',
                    buildingBlockNumber: '',
                    roomNumber: '',
                    remarks: ''
                },
                ruleValidate: {
                    cardNumber: [
                        { required: true, message:'请输入门禁卡号', trigger: 'blur' }
                    ],
                    householdName: [
                        { required: true, message: '请输入住户姓名', trigger: 'blur' },
                        { type: 'string', min: 1, message: '姓名格式错误', trigger: 'blur' }
                    ],
                    tel: [
                        { required: true, message: '请输入联系电话', trigger: 'blur' },
                        { type: 'string', min: 7, message: '联系电话格式错误', trigger: 'blur' }
                    ],
                    gender: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    buildingBlockNumber: [
                        { required: true, message: '请输入楼号/单元号', trigger: 'blur' }
                    ],
                    roomNumber: [
                        { required: true, message: '请输入房号', trigger: 'blur' }
                    ],
                    remarks: [
                        { type: 'string', min: 0, max: 200, message: '备注信息超过字数', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            init() {
                /* let data = this.$route.params;

                this.formValidate = data;
                this.formValidate.cardNumber= data.cardNumber;
                this.formValidate.householdName= data.householdName
                this.formValidate.tel= data.tel;
                this.formValidate.gender= data.gender;
                this.formValidate.buildingblockNumber= data.buildingblockNumber;
                this.formValidate.roomNumber= data.roomNumber;
                this.formValidate.remarks= data.remarks;
                this.formValidate.photoId= data.photoId
                this.formValidate.id= data.id;

                if (data.photoUrl){
                    this.photoUrl = data.photoUrl;
                } else if (!data.photoUrl) {
                    if (data.gender == '男'){
                        this.photoUrl = '../../images/avatars-man.png';
                    } else if (data.gender == '女') {
                        this.photoUrl = '../../images/avatars-woman.png';
                    }
                } */
            },
            handleFileUp() {
              document.getElementById('household_photoup').click();
            },
            handlePhotoRemove() {
              this.photoUrl = "/images/avatars-man.png";
            },
            handleSubmit(name) {
                let _this = this;
                _this.$refs[name].validate((valid) => {
                    if(valid) {
                        _this.dto.householdDto = _this.formValidate;
                        console.info(_this.dto);
                        axios.post(GlobalServer.addHouseHold,_this.dto)
                        .then(function(response){
                            let data = response.data;
                            if(data.error === null) {
                                console.info('保存成功');
                            }
                        })
                        .catch(function(error){
                            console.info(error);
                        });
                    /* this.$refs.addSubmit.click(); */
                    }
                });
            },
            handleFilechange() {
              var file = this.$refs.photoup.files[0];
              var reader = new FileReader();
              let _this = this;
              reader.readAsDataURL(file);
              reader.onload = function(event){
                _this.photoUrl = event.target.result;
              }
            },
            handleReset(name) {
                this.$refs[name].resetFields();
            }
        },
        created(){},
        mounted(){},
        activated () {
          /* this.init(); */
        }
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
  .photoView {
    float: left;
    width: 150px;
    position: relative;
  }
  .photoView img {
    width: 100%;
    position:static;
    z-index: 1;
  }

  .photoView .editWrap {
      position: absolute;
      top: 0;
      right: 0;
      z-index: 100;
      width: 20px;
  }

  .photoView .editWrap i {
    margin-bottom: 10px;
    width: 20px;
    cursor: pointer;
  }

  .validateView {
    margin-left: 20px;
    float: left;
    width: 30%;
  }
</style>