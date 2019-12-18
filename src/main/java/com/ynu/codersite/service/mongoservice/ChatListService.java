package com.ynu.codersite.service.mongoservice;

import com.alibaba.fastjson.JSONObject;
import com.ynu.codersite.entity.esentity.UserInfo;
import com.ynu.codersite.entity.mogoentity.ChatList;
import com.ynu.codersite.entity.mogoentity.User;
import com.ynu.codersite.repository.mongorepository.CharListRepository;
import com.ynu.codersite.service.esservice.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created on 2019/12/17 0017
 * BY Jianlong
 */
@Service
public class ChatListService {
    @Autowired
    CharListRepository charListRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserInfoService userInfoService;

    // 增加一条记录
    public void addChat(ChatList chatList){
        charListRepository.save(chatList);
    }

    // 删除一条记录
    public void deleteChat(String cid){
        charListRepository.deleteById(cid);
    }

    /**
     * 封装json对象
     * @param cl
     * @return
     */
    public JSONObject encapsulateJson(ChatList cl){
        JSONObject jsonObject = new JSONObject();
        String uid = cl.getUid();
        jsonObject.put("uid", uid);
        jsonObject.put("avatar", userService.getUserById(uid).getAvatarId());
        jsonObject.put("nickname", userInfoService.getUserById(uid).getNickname());
        jsonObject.put("time",cl.getTime());
        jsonObject.put("message",cl.getMessage());
        return jsonObject;
    }

    // 获取当前用户和某用户的私信
    public List<JSONObject> getMyChatListWithUser(String uid, String objId){
        List<ChatList> result = charListRepository.findByUidAndObjId(uid, objId);
        result.addAll(charListRepository.findByUidAndObjId(objId, uid));

        Collections.sort(result, new Comparator<ChatList>() {
            @Override
            public int compare(ChatList o1, ChatList o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        List<JSONObject> jsons = new ArrayList<>();
        for (ChatList cl:result){
            jsons.add(this.encapsulateJson(cl));
        }

        return jsons;
    }

    // 获取某用户的私信列表
    public List<JSONObject> getMyChatList(String uid){
        List<JSONObject> jsons = new ArrayList<>();
        Set<String> uidList = new HashSet<>();

        // 当前用户发送过的
        List<ChatList> aList = charListRepository.findByUid(uid);
        for (ChatList cl:aList){
            uidList.add(cl.getObjId());
        }

        // 发送给当前用户的
        List<ChatList> bList = charListRepository.findByObjId(uid);
        for (ChatList cl:bList){
            uidList.add(cl.getUid());
        }

        for (String u:uidList){
            List<ChatList> chatLists;
            List<ChatList> cList = charListRepository.findByUidAndObjId(u, uid);
            List<ChatList> dList = charListRepository.findByUidAndObjId(uid, u);

            if (cList == null){
                chatLists = dList;
            } else{
                if (dList == null){
                    chatLists = cList;
                } else {
                    cList.addAll(dList);
                    chatLists = cList;
                }
            }

            Collections.sort(chatLists, new Comparator<ChatList>() {
                @Override
                public int compare(ChatList o1, ChatList o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });

            jsons.add(encapsulateJson(chatLists.get(0)));
        }

        return jsons;
    }

}
