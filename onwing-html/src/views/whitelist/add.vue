<template>
    <div class="validateMain">
        <div class="photoView">
            <div class="editWrap" style="">
                <span class="icon-camera" @click="handleOpenCamera()"></span>
                <span class="icon-upload" @click="handleFileUp()"></span>
            </div>
            <canvas ref="cameraPhoto"></canvas>
            <form style="display:none">
                <input id="household_photoup" type="file" accept="image/*" name="photoUrl" @change="handleFilechange()" ref="photoup">
            </form>
            <!--拍照弹框-->
            <Modal
                v-model="cameraModel"
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
                <div class="readCard">
                    <Button @click="readCard">读身份证</Button>
                </div>
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

                <FormItem label="小区名称" prop="communityName">
                    <Select v-model="formValidate.communityID" :disabled="communityFlag">
                        <Option v-for="item in communityList" :value="item.value" :key="item.value" @click.native="selectCommunity('build')">{{item.label}}</Option>
                    </Select>
                </FormItem>

                <FormItem label="楼号" prop="buildingBlockNumber">
                    <Select v-model="formValidate.buildingBlockNumber" :disabled="buildingFlag">
                        <Option v-for="item in buildingList" :value="item" :key="item" @click.native="selectBuilding('apartment')">{{item}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="单元号" prop="apartmentNumber">
                    <Select v-model="formValidate.apartmentNumber" :disabled="apartmentFlag">
                        <Option v-for="item in apartmentList" :value="item" :key="item" @click.native="selectApartment('floor')">{{item}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="楼层" prop="floorNumber">
                    <Select v-model="formValidate.floorNumber" :disabled="floorFlag">
                        <Option v-for="item in floorList" :value="item" :key="item" @click.native="selectFloor('room')">{{item}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="房号" prop="roomNumber">
                    <Select v-model="formValidate.roomNumber" :disabled="roomFlag">
                        <Option v-for="item in roomList" :value="item" :key="item">{{item}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="业主类型" prop="householdType">
                    <RadioGroup v-model="formValidate.householdType">
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
            communityID: Cookies.get('communityID'),
            cameraModel: false,
            communityFlag: true,
            buildingFlag: true,
            apartmentFlag: true,
            floorFlag: true,
            roomFlag: true,
            communityList:[],
            communityIDList:[],
            communityNameList:[],
            searchCommunityID:'',
            buildingList:[],
            apartmentList:[],
            floorList: [],
            roomList:[],
            defaultPhotoUrl: require('../../images/avatars-man.png'),
            photoUrl: require('../../images/avatars-man.png'),
            formValidate: {
                householdName: '',
                gender: '',
                identifyCard: '',
                tel: '',
                communityID:'',
                communityName:'',
                buildingBlockNumber: '',
                apartmentNumber:'',
                floorNumber:'',
                roomNumber: '',
                householdType:'',
                cardNumber: '',
                remarks: ''
            },
            file:'',
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
                communityName: [
                    { required: true, message: '请填写小区', trigger: 'change' }
                ],
                buildingBlockNumber: [
                    { required: true, message: '请填写楼号', trigger: 'change' }
                ],
                apartmentNumber: [
                    { required: true, message: '请填写单元号', trigger: 'change' }
                ],
                floorNumber: [
                    { required: true, message: '请填写楼层', trigger: 'change' }
                ],
                roomNumber: [
                    { required: true, message: '请填写房号', trigger: 'change' }
                ],
                householdType: [
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
        init(){
            let access = Cookies.get('access'),
                obj = {},
                communityNameList=[],
                communityIDList = [],
                _length;

            communityNameList = sessionStorage.getItem('communityName').split(',');
            communityIDList = sessionStorage.getItem('communityID').split(',');
            this.communityList = [];
            this.communityIDList = [];
            this.communityNameList = [];
            this.communityIDList = communityIDList;
            this.communityNameList = communityNameList;
            _length = communityNameList.length;
            for(let i=0;i !== _length;i++){
                obj.label = communityNameList[i];
                obj.value = communityIDList[i];
                this.communityList.push(obj);
                obj={};
            }

            if (access === '0') {
                this.communityFlag = false;
            } else {
                this.formValidate.communityID = this.communityList[0].value;
                this.formValidate.communityName = this.communityList[0].value;
                this.searchCommunityID = this.communityList[0].value;
                this.getCommunityInfo('build');
            }

        },
        handleOpenCamera() {
            this.cameraModel = true;

            navigator.mediaDevices.enumerateDevices()
                .then(this.gotDevices)
                .then(this.getStream)
                .catch(this.handleError);
        },
        gotDevices(deviceInfos) {

            for (let i = 0; i !== deviceInfos.length; ++i) {
                let deviceInfo = deviceInfos[i],
                    option = document.createElement('option');
                option.value = deviceInfo.deviceId;

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

            let constraints = {
                video: {
                    deviceId: {exact: this.$refs.videoSelect.value}
                }
            };

            navigator.mediaDevices.getUserMedia(constraints).
            then(this.gotStream).catch(this.handleError);
        },
        gotStream(stream) {
            window.stream = stream; // make stream available to console
            this.$refs.videoElement.srcObject = stream;
        },
        stopStream () {
            if (window.stream) {
                let videoSelect = this.$refs.videoSelect;
                window.stream.getTracks().forEach( function(track) {
                    // statements
                    track.stop();
                });
                while(videoSelect.firstChild){
                  videoSelect.removeChild(videoSelect.firstChild);
                }
            }
        },
        handleError(error) {
            console.log('Error: ', error);
        },

        handleCameraOk() {
            let video = this.$refs.videoElement,
                canvas = this.$refs.cameraPhoto,
                context = canvas.getContext('2d'),
                _this = this;
            canvas.width=214;
            canvas.height=160;
            context.drawImage(video,0,0,214,160);
            canvas.toBlob(function(blob){
                _this.file = blob;
            });
            this.stopStream();
        },

        handleFileUp() {
            this.$refs.photoup.click();
        },

        handlePhotoRemove() {
            this.photoUrl = this.defaultPhotoUrl;
        },

        handleFilechange() {
            let _this = this;
            let file = _this.$refs.photoup.files[0],
                reader = new FileReader();
            _this.file = file;
            reader.readAsDataURL(file);
            reader.onload = function(){
                _this.drawToCanvas(this.result);
            };
        },

        drawToCanvas(imgData) {
            let canvas = this.$refs.cameraPhoto,
                context = canvas.getContext('2d'),
                img = new Image;
            canvas.width=214;
            canvas.height=160;
            img.src = imgData;
            img.onload = function(){
                context.drawImage(img,0,0,214,160);
            };
        },
        selectCommunity(nextFlag){
            let communityName = event.target.innerText,
                communityID_index;
            communityID_index = this.communityNameList.indexOf(communityName);
            this.buildingList = [];
            this.apartmentList = [];
            this.floorList = [];
            this.roomList = [];
            this.formValidate.communityID = this.communityIDList[communityID_index];
            this.formValidate.communityName = communityName;
            this.formValidate.buildingBlockNumber = '';
            this.formValidate.apartmentNumber = '';
            this.formValidate.floorNumber = '';
            this.formValidate.roomNumber = '';
            this.buildingFlag = true;
            this.apartmentFlag = true;
            this.floorFlag = true;
            this.roomFlag = true;
            this.searchCommunityID = this.communityIDList[communityID_index];
            this.getCommunityInfo(nextFlag);
        },
        selectBuilding(nextFlag) {
            this.apartmentList = [];
            this.floorList = [];
            this.roomList = [];
            this.formValidate.buildingBlockNumber = event.target.innerText;
            this.formValidate.apartmentNumber = '';
            this.formValidate.floorNumber = '';
            this.formValidate.roomNumber = '';
            this.apartmentFlag = true;
            this.floorFlag = true;
            this.roomFlag = true;
            this.getCommunityInfo(nextFlag);
        },
        selectApartment(nextFlag) {
            this.floorList = [];
            this.roomList = [];
            this.formValidate.apartmentNumber = event.target.innerText;
            this.formValidate.floorNumber = '';
            this.formValidate.roomNumber = '';
            this.floorFlag = true;
            this.roomFlag = true;
            this.getCommunityInfo(nextFlag);
        },
        selectFloor(nextFlag) {
            this.roomList = [];
            this.formValidate.floorNumber = event.target.innerText;
            this.formValidate.roomNumber = '';
            this.roomFlag = true;
            this.getCommunityInfo(nextFlag);
        },
        selectRoom() {

        },
        getCommunityInfo(nextFlag) {
            let _this = this,
                searchArg = [];
            searchArg.push(_this.searchCommunityID, _this.formValidate.buildingBlockNumber, _this.formValidate.apartmentNumber, _this.formValidate.floorNumber);
            console.info(_this.searchCommunityID);
            axios.get(GlobalServer.getCommunityInfo + '?parentNodePath=/' + searchArg.join('/').replace(/\/{2,}/g,''))
                .then(function(response){
                    let data = response.data;
                    if(data.error === null) {
                        if (nextFlag === 'build') {
                            _this.buildingList = data.childNodeNameList;
                            _this.buildingFlag = false;
                        } else if(nextFlag === 'apartment') {
                            _this.apartmentList = data.childNodeNameList;
                            _this.apartmentFlag = false;
                        } else if (nextFlag === 'floor') {
                            _this.floorList = data.childNodeNameList;
                            _this.floorFlag = false;
                        } else if (nextFlag === 'room') {
                            _this.roomList = data.childNodeNameList;
                            _this.roomFlag = false;
                        }
                    } else {
                        _this.$Message.error('该小区还没有详细的楼栋信息，请上传拓扑文件');
                    }
                })
                .catch(function(error){
                  console.info('err='+ error);
                });
        },
        handleSubmit(name) {
            let _this = this,
                room_path = '',
                formdata = new FormData(),
                config = {
                    headers:{
                        'Content-Type':'application/x-www-form-urlencoded'
                    }
                };
            _this.$refs[name].validate((valid) => {
                if(valid && _this.file !== '') {
                    room_path = '/' + _this.formValidate.buildingBlockNumber + '/' + _this.formValidate.apartmentNumber + '/' + _this.formValidate.floorNumber + '/' + _this.formValidate.roomNumber;
                    formdata.append('householdName',_this.formValidate.householdName);
                    formdata.append('gender',_this.formValidate.gender);
                    formdata.append('tel',_this.formValidate.tel);
                    formdata.append('identifyCard',_this.formValidate.identifyCard);
                    formdata.append('communityId',_this.formValidate.communityID);
                    formdata.append('communityName',_this.formValidate.communityName);
                    formdata.append('roomPath',room_path);
                    formdata.append('householdType',_this.formValidate.householdType);
                    formdata.append('cardNumber',_this.formValidate.cardNumber);
                    formdata.append('remarks',_this.formValidate.remarks);
                    formdata.append('file',_this.file);
                    axios.post(GlobalServer.addHouseHold,formdata,config)
                        .then(function(response){
                            let data = response.data;
                            if(data.error === null) {
                                _this.$Message.success('添加成功！');
                                _this.drawToCanvas(_this.photoUrl);
                                _this.handleReset('formValidate');
                                _this.householdDto = {};
                            }
                            _this.init();
                        })
                        .catch(function(error){
                            console.info(error);
                        });
                } else {
                  console.info('validate err');
                  console.info(_this.file);
                }
            });
        },
        handleReset(name) {
            this.$refs[name].resetFields();
            this.photoUrl = this.defaultPhotoUrl;
        },
        readCard() {
            console.info('readCard');
            let url = 'http://127.0.0.1:24010/ZKIDROnline/ScanReadIdCardInfo?OP-DEV=1&CMD-URL=4&REPEAT=1',
                _this = this;
            axios.get(url)
                .then(function(response){
                    let data = JSON.parse(response.data.replace(/[\t\n\r\s]/g,'').replace(/\\/g,'/'));
                    if (data.ret === 0) {
                        _this.formValidate.householdName = data.Certificate.Name;
                        _this.formValidate.gender = data.Certificate.Sex;
                        _this.formValidate.identifyCard = data.Certificate.IDNumber;
                    }
                    //console.info(_this.formValidate.householdName);
                })
                .catch(function(){

                })
        }
    },
    created(){
        this.init();
    },
    mounted(){
        this.$refs.videoSelect.onchange = this.getStream;
        this.$nextTick(function(){
          this.drawToCanvas(this.photoUrl);
        })

    },
    activated(){}
}
</script>
<style type="text/css" scoped>
  .formstyle {
    position: relative;
  }

  .formstyle .readCard {
      position: absolute;
      right: -100px;
  }
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
    width: 238px;
    position: relative;
  }

  .photoView img {
    width: 100%;
    position: static;
    z-index: 1;
  }

  .photoView canvas {
    position: static;
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
    height: 160px;
  }

  .validateView {
    /*margin-left: 20px;*/
    float: left;
    width: 30%;
  }
</style>
