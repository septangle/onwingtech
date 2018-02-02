<template>
    <div class="validateMain">
        <div class="photoView">
          <img :src="photoUrl">
        </div>
        <div class="validateView">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" class="formstyle">
                <FormItem label="门禁卡号" prop="cardNumber">
                    <Input v-model="formValidate.cardNumber"></Input>
                </FormItem>
                <FormItem label="姓名" prop="householdName">
                    <Input v-model="formValidate.householdName" placeholder="请输入住户姓名" disabled></Input>
                </FormItem>
                <FormItem label="性别" prop="gender">
                    <RadioGroup v-model="formValidate.gender">
                        <Radio label="男" disabled>男</Radio>
                        <Radio label="女" disabled>女</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="身份证" prop="identifyCard">
                    <Input v-model="formValidate.identifyCard" placeholder="请填写身份证号" disabled></Input>
                </FormItem>
                <FormItem label="联系电话" prop="tel">
                    <Input v-model="formValidate.tel" placeholder="请输入联系电话"></Input>
                </FormItem>
                <FormItem label="楼号/单元号" prop="buildingblockNumber">
                  <Input v-model="formValidate.buildingBlockNumber"></Input>
                </FormItem>
                <FormItem label="房号" prop="roomNumber">
                  <Input v-model="formValidate.roomNumber"></Input>
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
        name:'whitelist_info_index',
        data () {
            return {
                formValidate: {
                    cardNumber: '',
                    householdName: '',
                    gender: '',
                    identifyCard:'',
                    tel: '',
                    buildingBlockNumber: '',
                    roomNumber: '',
                    remarks: '',
                    photoId: '',
                    id: ''
                },
                photoUrl:'',
                ruleValidate: {
                    cardNumber: [
                        { required: false },
                        { type:'string',min: 0,max: 20,message:'门禁卡格式错误', trigger: 'blur'}
                    ],
                    householdName: [
                        { required: true, message: '请填写姓名', trigger: 'blur' },
                        { type: 'string', min: 1, message: '姓名格式错误', trigger: 'blur' }
                    ],
                    gender: [
                       { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    identifyCard: [
                        { required: true, message: '请填写访客身份证号', trigger: 'blur' },
                        { type: 'string', min: 15, max: 18, message: '身份证号格式错误', trigger: 'blur' }
                    ],
                    tel: [
                        { required: true, message: '请填写联系电话', trigger: 'blur' },
                        { type: 'string', min: 7, message: '联系电话格式错误', trigger: 'blur' }
                    ],
                    buildingBlockNumber: [
                        { required: true, message: '请填写楼号/单元号', trigger: 'blur' }
                    ],
                    roomNumber: [
                        { required: true, message: '请填写房号', trigger: 'blur' }
                    ],
                    remarks: [
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
                this.formValidate.householdName= data.householdName;
                this.formValidate.gender= data.gender;
                this.formValidate.identifyCard = data.identifyCard;
                this.formValidate.tel= data.tel;
                this.formValidate.buildingBlockNumber= data.buildingBlockNumber;
                this.formValidate.roomNumber= data.roomNumber;
                this.formValidate.remarks= data.remarks;
                this.formValidate.photoId= data.photoId;
                this.formValidate.id= data.id;

                if (data.photoId){
                    this.photoUrl = GlobalServer.ServerHost + 'onwing-web/' + data.photoId;
                } else if (!data.photoId) {
                    if (data.gender == '男'){
                        this.photoUrl = GlobalServer.ServerHost + 'onwing-web/' + 'image/avatars-man.png';
                    } else if (data.gender == '女') {
                        this.photoUrl = GlobalServer.ServerHost + 'onwing-web/' + 'image/avatars-woman.png';
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
                        dto.householdDto.cardNumber = _this.formValidate.cardNumber;
                        dto.householdDto.tel = _this.formValidate.tel;
                        dto.householdDto.buildingBlockNumber = _this.formValidate.buildingBlockNumber;
                        dto.householdDto.roomNumber = _this.formValidate.roomNumber;
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
        created(){
            this.init();
        },
        activated () {},
        watch: {
            '$route' () {
                this.init();
            }
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
