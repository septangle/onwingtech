<template>
    <div class="validateMain">
        <div class="photoView">
                <div class="editWrap" style="">
                    <span class="icon-camera" @click="handleOpenCamera()"></span>
                    <span class="icon-upload" @click="handleFileUp()"></span>
                    <!--<Icon size="24" type="edit" @click.native="handleFileUp()"></Icon>
                    <Icon size="30" type="trash-b" @click.native="handlePhotoRemove()"></Icon>-->
                </div>
                <img :src="photoUrl" ref="img">
            <form style="display:none">
                <input id="household_photoup" type="file" accept="image/*" name="photoUrl" @change="handleFilechange()" ref="photoup">
            </form>
            <!--拍照弹框-->
            <Modal
                v-model="model1"
                title="照片采集"
                ok-text="拍照"
                cancel-text="关闭"
                :mask-closable="false"
                @on-ok="handleCameraOk"
                @on-cancel="stopStream">
                <div id="container">
                  <div class="select">
                    <label for="videoSource">请选择摄像头: </label><select id="videoSource" ref="videoSelect"></select>
                  </div>
                  <video class="video" muted autoplay ref="videoElement"></video>
                </div>
            </Modal>
        </div>

        <div class="validateView">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100" class="formstyle">
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
                <FormItem label="小区名称" prop="estateName">
                    <Select v-model="formValidate.estateName">
                        <Option v-for="item in estateList" :value="item.value" :key="item.value">{{item.label}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="楼号" prop="buildingBlockNumber">
                    <Select v-model="formValidate.buildingBlockNumber">
                        <Option v-for="item in buildingblockList" :value="item.value" :key="item.value">{{item.label}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="单元号" prop="apartmentNumber">
                    <Select v-model="formValidate.apartmentNumber">
                        <Option v-for="item in apartmentList" :value="item.value" :key="item.key">{{item.label}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="楼层" prop="floorNumber">
                    <Select v-model="formValidate.floorNumber">
                        <Option v-for="item in floorList" :value="item.value" :key="item.key">{{item.label}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="房号" prop="roomNumber">
                    <Select v-model="formValidate.roomNumber">
                        <Option v-for="item in roomList" :value="item.value" :key="item.value">{{item.label}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="业主类型" prop="ownerType">
                    <RadioGroup v-model="formValidate.ownerType">
                        <Radio label="住户">住户</Radio>
                        <Radio label="租客">租客</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="门禁卡号" prop="cardNumber">
                    <Input v-model="formValidate.cardNumber" placeholder="请填写门禁卡号"></Input>
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
import Cookies from 'js-cookie';
export default {
    name:'whitelist_add_index',
    data () {
        return {
            model1:false,
            defaultPhotoUrl: require('../../images/avatars-man.png'),
            photoUrl: require('../../images/avatars-man.png'),
            formValidate: {
                householdName: '',
                gender: '男',
                identifyCard: '',
                tel: '',
                estateName:'',
                buildingBlockNumber: '',
                apartmentNumber:'',
                floorNumber:'',
                roomNumber: '',
                ownerType:'',
                cardNumber: '',
                remarks: ''
            },
            file:'',
            estateList:[
                {
                    value: 'e0001',
                    label: '绿地花园'
                },
                {
                    value: 'e0002',
                    label: '北水湾'
                },
                {
                    value: 'e0003',
                    label: '汤臣一品'
                },
                {
                    value: 'e0004',
                    label: '东方明珠'
                }
            ],
            buildingblockList:[
                {
                    value:'b0001',
                    label:'1号楼'
                },
                {
                    value:'b0002',
                    label:'2号楼'
                },
                {
                    value:'b0003',
                    label:'3号楼'
                },
                {
                    value:'b0004',
                    label:'4号楼'
                }
            ],
            apartmentList:[
                {
                    value:'a0001',
                    label:'1单元'
                },
                {
                    value:'a0002',
                    label:'2单元'
                },
                {
                    value:'a0003',
                    label:'3单元'
                }
            ],
            floorList: [],
            roomList:[],
            ruleValidate: {
                householdName: [
                    { required: true, message: '请填写姓名', trigger: 'blur' },
                    { type: 'string', pattern: /^[^1-9a-zA-Z\~\!\@\#\$\%\^\&\*\(\)\_\+\`\-\=\{\}\[\]\|\\\:\"\;\'\<\>\?\,\.\/\先生\女士]{2,}$/g, message: '姓名格式错误', trigger: 'blur' }
                ],
                gender: [
                    { required: true, message: '请选择性别', trigger: 'change' }
                ],
                identifyCard: [
                    { required: true, message: '请填写身份证号', trigger: 'blur' },
                    { type: 'string', pattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$|^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$/g, message: '身份证号格式错误', trigger: 'blur' }
                ],
                tel: [
                    { required: true, message: '请填写联系电话', trigger: 'blur' },
                    { type: 'string', pattern:/^\d{7,12}$/g, message: '联系电话格式错误', trigger: 'blur' }
                ],
                estateName: [
                    { required: true, message: '请填写小区', trigger: 'blur' }
                ],
                buildingBlockNumber: [
                    { required: true, message: '请填写楼号', trigger: 'blur' }
                ],
                apartmentNumber: [
                    { required: true, message: '请填写单元号', trigger: 'blur' }
                ],
                floorNumber: [
                    { required: true, message: '请填写楼层', trigger: 'blur' }
                ],
                roomNumber: [
                    { required: true, message: '请填写房号', trigger: 'blur' }
                ],
                ownerType: [
                    { required: true, message: '请选择业主类型', trigger: 'change' }
                ],
                cardNumber: [
                    { required: false, message: '请输入门禁卡号', trigger: 'blur' },
                    { type: 'string', pattern: /^[0-9a-zA-Z]*$/g, message: '门禁卡格式错误', trigger: 'blur'}
                ],
                remarks: [
                    { type: 'string', min: 0, max: 200, message: '备注信息超过字数', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        handleOpenCamera() {
            this.model1 = true;
            //console.info(this.$refs.videoElement);
            navigator.mediaDevices.enumerateDevices()
                .then(this.gotDevices)
                .then(this.getStream)
                .catch(this.handleError);
        },
        gotDevices(deviceInfos) {
            //console.info(deviceInfos);
            for (var i = 0; i !== deviceInfos.length; ++i) {
                var deviceInfo = deviceInfos[i],
                    option = document.createElement('option');
                option.value = deviceInfo.deviceId;
                /*if (deviceInfo.kind === 'audioinput') {
                    option.text = deviceInfo.label || 'microphone ' + (audioSelect.length + 1);
                    audioSelect.appendChild(option);
                }*/
                if (deviceInfo.kind === 'videoinput') {
                    option.text = deviceInfo.label || 'camera ' + (this.$refs.videoSelect.length + 1);
                    this.$refs.videoSelect.appendChild(option);
                } else {
                    //console.log('Found one other kind of source/device: ', deviceInfo);
                }
            }
        },
        getStream() {
            if (window.stream) {
                window.stream.getTracks().forEach(function(track) {
                    //console.info(track);
                    track.stop();
                });
            }

            var constraints = {
                video: {
                    deviceId: {exact: this.$refs.videoSelect.value}
                }
            };

            console.info(constraints);
            navigator.mediaDevices.getUserMedia(constraints).
            then(this.gotStream).catch(this.handleError);
        },
        gotStream(stream) {
            window.stream = stream; // make stream available to console
            this.$refs.videoElement.srcObject = stream;
        },
        stopStream () {
            if (window.stream) {
                let ele = this.$refs.videoSelect;
                window.stream.getTracks().forEach( function(track) {
                    // statements
                    track.stop();
                });
                //console.info(this.$refs.videoSelect.childNodes);
                while(ele.firstChild){
                    ele.removeChild(ele.firstChild);
                }
            }
        },
        handleError(error) {
            console.log('Error: ', error);
        },

        handleCameraOk() {
            console.info(this.model1);
        },

        handleFileUp() {
          document.getElementById('household_photoup').click();
        },

        handlePhotoRemove() {
          this.photoUrl = this.defaultPhotoUrl;
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
            this.photoUrl = this.defaultPhotoUrl;
        }
    },
    created(){},
    mounted(){
        this.$refs.videoSelect.onchange = this.getStream;
    },
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
    padding-right: 24px;
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
      width: 24px;
      text-align: right;
  }

  .photoView .editWrap span {
    margin-bottom: 2px;
    width: 20px;
    height: 20px;
    cursor: pointer;
    display: inline-block;
  }

  .editWrap span.icon-camera {
    background-image: url("../../images/icon_camera.png");
    background-repeat: no-repeat;
    background-size: 100%;
  }

  .editWrap span.icon-upload {
    background-image: url("../../images/icon_cloud_upload.png");
    background-repeat: no-repeat;
    background-size: 100%;
  }
  video {
    margin-top: 10px;
    width: 480px;
    height: 270px;
  }
  .validateView {
    margin-left: 20px;
    float: left;
    width: 30%;
  }
</style>
