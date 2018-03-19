const test = '//192.168.1.102';
const Origin = location.origin;
const ServerHost = Origin + ':8080/';
const login = ServerHost + 'onwing-web/adminiStator/login.do';
const addUser = ServerHost + 'onwing-web/adminiStator/addUser.do';
const findAllUser = ServerHost + 'onwing-web/adminiStator/findAllUser.do';
const findAllHouseHold = ServerHost + 'onwing-web/household/findAllHouseHold.do';
const updateHouseHold = ServerHost + 'onwing-web/household/updateHouseHold.do';
const addHouseHold = ServerHost + 'onwing-web/household/addHouseHold.do';
const removeHouseHold = ServerHost + 'onwing-web/household/removeHouseHold.do';
const findHouseHoldById = ServerHost + 'onwing-web/household/findHouseHoldById.do';
const findAllAccessRecord = ServerHost + 'onwing-web/accessRecord/findAllAccessRecord.do';
const uploadPhotos = ServerHost + 'onwing-web/addphotos/uploadPhotos.do';
const addStranger = ServerHost + 'onwing-web/stranger/addStranger.do';
const getAllStranger = ServerHost + 'onwing-web/stranger/getAllStranger.do';
const getStrangerLevel = ServerHost + 'onwing-web/stranger/getStrangerLevel.do';
const findAllStrangerAccessRecord = ServerHost + 'onwing-web/strangerAccessRecord/findAllStrangerAccessRecord.do';
const getCommunityInfo = ServerHost + 'onwing-web/community/getChildNodes.do';
const createCommunity = ServerHost + 'onwing-web/community/createCommunity.do';
const importRoomsFromCvs = ServerHost + 'onwing-web/community/importRoomsFromCsv.do';
export default {
    ServerHost,
    login,
    addUser,
    findAllUser,
    findAllHouseHold,
    updateHouseHold,
    addHouseHold,
    removeHouseHold,
    findHouseHoldById,
    findAllAccessRecord,
    uploadPhotos,
    addStranger,
    getAllStranger,
    getStrangerLevel,
    findAllStrangerAccessRecord,
    getCommunityInfo,
    createCommunity,
    importRoomsFromCvs
};
