package com.ynu.codersite.service.mongoservice;

import com.ynu.codersite.entity.mogoentity.ChatList;
import com.ynu.codersite.repository.mongorepository.CharListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/12/17 0017
 * BY Jianlong
 */
@Service
public class ChatListService {
    @Autowired
    CharListRepository charListRepository;

    // 增加一条记录
    public void addChat(ChatList chatList){
        charListRepository.save(chatList);
    }

    // 删除一条记录
    public void deleteChat(String cid){
        charListRepository.deleteById(cid);
    }
}
