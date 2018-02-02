const Origin = location.origin;
const ServerHost = Origin + ':8080/';
const login = ServerHost + 'onwing-web/adminiStator/login.do';
const findAllHouseHold = ServerHost + 'onwing-web/household/findAllHouseHold.do';
const updateHouseHold = ServerHost + 'onwing-web/household/updateHouseHold.do';
const addHouseHold = ServerHost + 'onwing-web/household/addHouseHold.do';
const removeHouseHold = ServerHost + 'onwing-web/household/removeHouseHold.do';
const findAllAccessRecord = ServerHost + 'onwing-web/accessRecord/findAllAccessRecord.do';
const uploadPhotos = ServerHost + 'onwing-web/addphotos/uploadPhotos.do';
const addStranger = ServerHost + 'onwing-web/stranger/addStranger.do';
const getAllStranger = ServerHost + 'onwing-web/stranger/getAllStranger.do';
const getStrangerLevel = ServerHost + 'onwing-web/stranger/getStrangerLevel.do';
const findAllStrangerAccessRecord = ServerHost + 'onwing-web/strangerAccessRecord/findAllStrangerAccessRecord.do';

export default {
    ServerHost,
    login,
    findAllHouseHold,
    updateHouseHold,
    addHouseHold,
    removeHouseHold,
    findAllAccessRecord,
    uploadPhotos,
    addStranger,
    getAllStranger,
    getStrangerLevel,
    findAllStrangerAccessRecord
};
