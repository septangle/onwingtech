<template>
    <div class="validateMain">
        <div class="photoView">
            <template>
                <div class="editWrap" style="">
                    <Icon size="24" type="edit" @click.native="handleFileUp()"></Icon>
                    <Icon size="30" type="trash-b" @click.native="handlePhotoRemove()"></Icon>
                </div>
                <img :src="photoUrl" ref="img">
            </template>
            <form style="display:none">
                <input id="household_photoup" type="file" accept="image/*" name="photoUrl" @change="handleFilechange()" ref="photoup">
            </form>
        </div>

        <div class="validateView">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" class="formstyle">
                <FormItem label="门禁卡号" prop="cardNumber">
                    <Input v-model="formValidate.cardNumber" placeholder="请填写门禁卡号"></Input>
                </FormItem>
                <FormItem label="姓名" prop="householdName">
                    <Input v-model="formValidate.householdName" placeholder="请填写住户姓名"></Input>
                </FormItem>
                <FormItem label="性别" prop="gender">
                    <RadioGroup v-model="formValidate.gender">
                        <Radio label="男">男</Radio>
                        <Radio label="女">女</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="身份证" prop="identifyCard">
                  <Input v-model="formValidate.identifyCard" placeholder="请填写身份证号"></Input>
                </FormItem>
                <FormItem label="联系电话" prop="tel">
                    <Input v-model="formValidate.tel" placeholder="请填写联系电话"></Input>
                </FormItem>
                <FormItem label="科室" prop="buildingBlockNumber">
                  <Input v-model="formValidate.buildingBlockNumber"></Input>
                </FormItem>
                <FormItem label="办公室" prop="roomNumber">
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

        </div>
    </div>
</template>
<script>
    import GlobalServer from '../../config.js';
    import axios from 'axios';

    export default {
        name:'whitelist_add_index',
        data () {
            return {
                photoUrl:"/images/avatars-man.png",
                formValidate: {
                    cardNumber: '',
                    householdName: '',
                    gender: '',
                    identifyCard: '',
                    tel: '',
                    buildingBlockNumber: '',
                    roomNumber: '',
                    remarks: ''
                },
                file:'',
                ruleValidate: {
                    cardNumber: [
                        { required: true, message: '请输入门禁卡号', trigger: 'blur' },
                        { type: 'string', pattern: /^[0-9a-zA-Z]*$/g, message: '门禁卡格式错误', trigger: 'blur'}
                    ],
                    householdName: [
                        { required: true, message: '请填写姓名', trigger: 'blur' },
                        { type: 'string', pattern: /^[^1-9a-zA-Z\~\!\@\#\$\%\^\&\*\(\)\_\+\`\-\=\{\}\[\]\|\\\:\"\;\'\<\>\?\,\.\/\先生\女士]{2,}$/g, message: '姓名格式错误', trigger: 'blur' }
                    ],
                    gender: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    identifyCard: [
                        { required: true, message: '请填写访客身份证号', trigger: 'blur' },
                        { type: 'string', pattern: /^[1-9](\d{14}|\d{17})$/g, message: '身份证号格式错误', trigger: 'blur' }
                    ],
                    tel: [
                        { required: true, message: '请填写联系电话', trigger: 'blur' },
                        { type: 'string', pattern:/^\d{7,12}$/g, message: '联系电话格式错误', trigger: 'blur' }
                    ],
                    buildingBlockNumber: [
                        { required: true, message: '请填写科室', trigger: 'blur' }
                    ],
                    roomNumber: [
                        { required: true, message: '请填写办公室', trigger: 'blur' }
                    ],
                    remarks: [
                        { type: 'string', min: 0, max: 200, message: '备注信息超过字数', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleFileUp() {
              document.getElementById('household_photoup').click();
            },
            handlePhotoRemove() {
              this.photoUrl = "/images/avatars-man.png";
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
                        formdata.append('cardNumber',_this.formValidate.cardNumber);
                        formdata.append('householdName',_this.formValidate.householdName);
                        formdata.append('gender',_this.formValidate.gender);
                        formdata.append('tel',_this.formValidate.tel);
                        formdata.append('identifyCard',_this.formValidate.identifyCard);
                        formdata.append('buildingBlockNumber',_this.formValidate.buildingBlockNumber);
                        formdata.append('roomNumber',_this.formValidate.roomNumber);
                        formdata.append('remarks',_this.formValidate.remarks);
                        formdata.append('file',_this.file);
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
                this.photoUrl = '/images/avatars-man.png';
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
