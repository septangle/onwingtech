<template>
  <div class="validateMain">
      <div class="photoView">
          <div class="editWrap">
              <span class="icon-camera" @click="handleOpenCamera()"></span>
              <span class="icon-upload" @click="handleFileUp()"></span>
          </div>
          <canvas ref="cameraPhoto"></canvas>
          <form style="display:none">
              <input id="household_photoup" type="file" accept="image/*" name="photoUrl" @change="handleFilechange()"
                   ref="photoup">
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
                  <Input v-model="formValidate.identifyCard" placeholder="请填写身份证号" disabled></Input>
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
                  <Select  v-model="formValidate.buildingBlockNumber" :disabled="buildingFlag">
                      <Option v-for="item in buildingList" :value="item" :key="item" @click.native="selectBuilding('apartment')">{{item}}</Option>
                  </Select>
              </FormItem>
              <FormItem label="单元号" prop="apartmentNumber">
                  <Select v-model="formValidate.apartmentNumber" :disabled="apartmentFlag" >
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
                  <Button type="primary" @click="handleSubmit('formValidate')">修改</Button>
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
    name:'whitelist_edit_index',
    data () {
        return {
            cameraModel: false,
            communityID: '',
            communityFlag: true,
            buildingFlag: false,
            apartmentFlag: false,
            floorFlag: false,
            roomFlag: false,
            communityList:[],
            communityIDList:[],
            communityNameList:[],
            searchCommunityID:'',
            buildingList:[],
            apartmentList:[],
            floorList: [],
            roomList:[],
            tempArr:[],
            photoUrl:'',
            file:'',
            formValidate: {
                id: '',
                photoUrl: '',
                householdName: '',
                gender: '',
                identifyCard:'',
                tel: '',
                communityName:'',
                communityID:'',
                buildingBlockNumber: '',
                apartmentNumber:'',
                floorNumber:'',
                roomNumber: '',
                householdType:'',
                cardNumber: '',
                remarks: '',
            },
            ruleValidate: {
                householdName: [
                    { required: true, message: '请填写姓名', trigger: 'blur' },
                    { type: 'string', min: 1, message: '姓名格式错误', trigger: 'blur' }
                ],
                gender: [
                    { required: true, message: '请选择性别', trigger: 'change' }
                ],
                identifyCard: [
                    { required: true, message: '请填写身份证号', trigger: 'blur' },
                    { type: 'string', min: 15, max: 18, message: '身份证号格式错误', trigger: 'blur' }
                ],
                tel: [
                    { required: true, message: '请填写联系电话', trigger: 'blur' },
                    { type: 'string', min: 7, message: '联系电话格式错误', trigger: 'blur' }
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
                    { required: false },
                    { type:'string',min: 0,max: 20,message:'门禁卡格式错误', trigger: 'blur'}
                ],
                remarks: [
                    { type: 'string', min: 0, max: 200, message: '备注信息超过字数', trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        init() {
            let data = this.$route.params,
                access = Cookies.get('access'),
                communityNameList=[],
                communityIDList = [];

            communityNameList = sessionStorage.getItem('communityName').split(',');
            communityIDList = sessionStorage.getItem('communityID').split(',');
            this.communityIDList = communityIDList;
            this.communityNameList = communityNameList;

            this.communityList = data.communityList;
            this.formValidate = data.formdate;

            if (access === '0') {
                this.communityFlag = false;
                this.formValidate.communityID = data.formdate.communityName;
                this.formValidate.communityName = data.formdate.communityName;
                this.searchCommunityID = data.formdate.communityName;
            } else {
                this.formValidate.communityID = this.communityList[0].value;
                this.formValidate.communityName = this.communityList[0].value;
                this.searchCommunityID = this.communityList[0].value;
            }
            console.info(this.communityList);

        },
        canvasLoadPhoto() {
            let canvas = this.$refs.cameraPhoto,
                context = canvas.getContext('2d'),
                img = new Image();
            img.src = this.formValidate.photoUrl;
            canvas.width = 214;
            canvas.height = 160;
            context.drawImage(img,0,0,214,160);
            /*canvas.toBlob(function(blob){
                _this.file = blob;
            });*/
        },
        handleOpenCamera() {
        /*打开摄像头拍照弹框*/
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

                }
            }
        },
        getStream() {
            if (window.stream) {
                window.stream.getTracks().forEach(function (track) {
                    track.stop();
                });
            }

            let constraints = {
                video: {
                    deviceId: {exact: this.$refs.videoSelect.value}
                }
            };

            navigator.mediaDevices.getUserMedia(constraints).then(this.gotStream).catch(this.handleError);
        },
        gotStream(stream) {
            window.stream = stream; // make stream available to console
            this.$refs.videoElement.srcObject = stream;
        },
        stopStream() {
        /*停止摄像头*/
            if (window.stream) {
                let videoSelect = this.$refs.videoSelect;
                window.stream.getTracks().forEach(function (track) {
                  // statements
                    track.stop();
                });

                while (videoSelect.firstChild) {
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
            canvas.width = 214;
            canvas.height = 160;
            context.drawImage(video, 0, 0, 214, 160);
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
            reader.onload = function (event) {
                _this.drawToCanvas(this.result);
            };
        },

        drawToCanvas(imgData) {
            let canvas = this.$refs.cameraPhoto,
                context = canvas.getContext('2d'),
                img = new Image;
            canvas.width = 214;
            canvas.height = 160;
            img.src = imgData;
            img.onload = function () {
                context.drawImage(img, 0, 0, 214, 160);
            };
        },

        handleSubmit (name) {
            let _this = this,
                room_path = '',
                formdata = new FormData(),
                config = {
                    headers:{
                      'Content-Type':'application/x-www-form-urlencoded'
                    }
                };

            this.$refs[name].validate((valid) => {
                if (valid) {
                    room_path = '/' + _this.formValidate.buildingBlockNumber + '/' + _this.formValidate.apartmentNumber + '/' + _this.formValidate.floorNumber + '/' + _this.formValidate.roomNumber;
                    formdata.append('id', _this.formValidate.id);
                    formdata.append('householdName',_this.formValidate.householdName);
                    formdata.append('gender',_this.formValidate.gender);
                    formdata.append('tel',_this.formValidate.tel);
                    formdata.append('identifyCard',_this.formValidate.identifyCard);
                    //formdata.append('communityId',sessionStorage.getItem('communityID'));
                    //formdata.append('communityName',sessionStorage.getItem('communityName'));
                    formdata.append('roomPath',room_path);
                    formdata.append('householdType',_this.formValidate.householdType);
                    formdata.append('cardNumber',_this.formValidate.cardNumber);
                    formdata.append('remarks',_this.formValidate.remarks);
                    formdata.append('file',_this.file);

                    /* 表单内容验证通过则开始提交修改 */
                    axios.post(GlobalServer.updateHouseHold,formdata,config)
                        .then(function(response){
                            let data = response.data;
                            if(data.error == null) {
                                _this.$Message.success('住户信息更新成功!');
                            } else {
                                _this.$Message.error(data.error.message);
                            }
                        })
                        .catch()
                } else {
                    _this.$Message.error('请检查是否有信息未填写!');
                }
            });
        },
        handleReset (name) {
            this.$refs[name].resetFields();
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
                    }
                })
                .catch(function(error){
                    console.info('err='+ error);
                });
        },
        getCommunityInfo_init(){
            let tempSearchArg = [],
                searchArg = [],
                str,
                _this = this;
            str = this.formValidate.communityID + ',' + this.formValidate.buildingBlockNumber + ',' + this.formValidate.apartmentNumber + ',' + this.formValidate.floorNumber;
            tempSearchArg = str.split(',');
            tempSearchArg.forEach(function(value,index){
                searchArg.push(value);
                axios.get(GlobalServer.getCommunityInfo + '?parentNodePath=/' + searchArg.join('/'))
                    .then(function(response){
                        let data = response.data;
                        if(data.error === null){
                            if(index === 0 ) {
                                _this.buildingList = data.childNodeNameList
                            } else if(index === 1) {
                                _this.apartmentList = data.childNodeNameList
                            } else if(index === 2) {
                                _this.floorList = data.childNodeNameList
                            } else {
                                _this.roomList = data.childNodeNameList
                            }
                        }
                    })
                    .catch(function(){

                    })
            });
        }
    },
    created(){
        this.init();
        this.getCommunityInfo_init();
    },
    activated () {

    },
    mounted() {
        this.$refs.videoSelect.onchange = this.getStream;
        this.$nextTick(function(){
            this.canvasLoadPhoto();
        })
    },
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
