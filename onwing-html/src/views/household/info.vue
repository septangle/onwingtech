<template>
    <div class="validateMain">
        <div class="photoView">
          <img :src="baseUrl + photoUrl">
        </div>
        <div class="validateView">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" class="formstyle">
                <FormItem label="门禁卡号" prop="cardNumber">
                    <Input v-model="formValidate.cardNumber" disabled></Input>
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
                <FormItem label="楼号/单元号" prop="buildingblockNumber">
                  <Input v-model="formValidate.buildingblockNumber" disabled></Input>
                </FormItem>
                <FormItem label="房号" prop="roomNumber">
                  <Input v-model="formValidate.roomNumber" disabled></Input>
                </FormItem>
                <FormItem label="备注" prop="remarks">
                    <Input v-model="formValidate.remarks" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="备注信息不要超过200字"></Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" @click="handleSubmit('formValidate')">修改</Button>
                </FormItem>
            </Form>
        </div>
    </div>
</template>
<script>
    import axios from 'axios';
    import GlobalServer from '../../config.js';
    export default {
        name:'household_info',
        data () {
            return {
                formValidate: {
                    cardNumber: '',
                    householdName: '',
                    tel: '',
                    gender: '',
                    buildingblockNumber: '',
                    roomNumber: '',
                    remarks: '',
                    photoId: '',
                    id: ''
                },
                photoUrl:'',
                baseUrl:GlobalServer.ServerHost + 'onwing-web/',
                ruleValidate: {
                    cardnumber: [
                        { required: false }
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
                        { required: true, message: 'Please select gender', trigger: 'change' }
                    ],
                    buildingblockNumber: [
                        { required: true, message: '请输入楼号/单元号', trigger: 'blur' }
                    ],
                    roomNumber: [
                        { required: true, message: '请输入房号', trigger: 'blur' }
                    ],
                    remarks: [
                        { required: false, message: 'Please enter a personal introduction', trigger: 'blur' },
                        { type: 'string', min: 0, max: 200, message: '备注信息超过字数', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            init() {
                let data = this.$route.params;

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
                }
            },
            handleSubmit (name) {
                let _this = this;
                let dto = {};
                dto.householdDto = {};
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        dto.householdDto.id = _this.formValidate.id;
                        dto.householdDto.gender = _this.formValidate.gender;
                        dto.householdDto.tel = _this.formValidate.tel;
                        dto.householdDto.householdName = _this.formValidate.householdName;
                        dto.householdDto.remarks = _this.formValidate.remarks;
                        /* 表单内容验证通过则开始提交修改 */
                        axios.post(GlobalServer.updateHouseHold,dto)
                        .then(function(response){
                        let data = response.data;
                            if(data.error == null) {
                                _this.$Message.success('住户信息更新成功!');
                            } else {
                                _this.$Message.error(data.error.message);
                            }
                        })
                    } else {
                        _this.$Message.error('请检查是否有信息未填写!');
                    }
                });
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            }
        },
        created(){},
        activated () {
          this.init();
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
    height: 100px;
  }
  .photoView img {
    width: 100%;
  }
  .validateView {
    margin-left: 20px;
    float: left;
    width: 30%;
  }
</style>