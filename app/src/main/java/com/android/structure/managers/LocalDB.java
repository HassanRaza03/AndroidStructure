//package com.structure.managers;
//
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.util.Log;
//import android.util.Pair;
//
//import com.structure.AppConstants;
//import com.structure.BaseApplication;
//import com.structure.Events;
//import com.structure.enums.CallStatus;
//import com.structure.enums.CallType;
//import com.structure.enums.ChatType;
//import com.structure.enums.ContactStatus;
//import com.structure.enums.FilterType;
//import com.structure.enums.MediaStatus;
//import com.structure.enums.MediaType;
//import com.structure.enums.MessageStatus;
//import com.structure.enums.MessageType;
//import com.structure.enums.MuteType;
//import com.structure.enums.RequestStatus;
//import com.structure.enums.StanzaStatus;
//import com.structure.models.CallLogModel;
//import com.structure.models.ChatModel;
//import com.structure.models.ChatSettingModel;
//import com.structure.models.ContactDetailModel;
//import com.structure.models.GroupDetailModel;
//import com.structure.models.GroupMessageInfo;
//import com.structure.models.MessageModel;
//import com.structure.models.ParticipantModel;
//import com.structure.models.RecipientModel;
//import com.structure.models.StanzaModel;
//import com.structure.util.Helper;
//import com.structure.xmpp.XMPPManager;
//import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
//
//import org.jivesoftware.smack.packet.Stanza;
//import org.jivesoftware.smackx.vcardtemp.packet.VCard;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import io.realm.Case;
//import io.realm.Realm;
//import io.realm.RealmList;
//import io.realm.RealmModel;
//import io.realm.RealmQuery;
//import io.realm.RealmResults;
//import io.realm.Sort;
//
//import static com.structure.R.string.status;
//import static io.realm.Realm.getDefaultInstance;
//
//public class LocalDB {
//    private static final String TAG = "LOCAL_DB";
//    /* Contact Related Methods  */
//
//    private static Realm openRealmInstance() {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        return realm;
//    }
//
//    private static void closeRealmInstance(Realm realm) {
//        realm.commitTransaction();
//        realm.close();
//
//    }
//
//    public static ArrayList<ContactDetailModel> getContacts() {
//        ArrayList<ContactDetailModel> listContacts = new ArrayList<>();
//        listContacts.addAll(
//                Realm.getDefaultInstance().copyFromRealm(
//                        RealmManager.getInstance().getData(ContactDetailModel.class)
//                                .in("status", new Integer[]{ContactStatus.getStatus(ContactStatus.REGISTERED), ContactStatus.getStatus(ContactStatus.UNREGISTERED)})
//                                .findAll()
//                                .sort("status", Sort.DESCENDING, "name", Sort.ASCENDING))
//        );
//
//        return listContacts;
//    }
//
//    public static ArrayList<ChatModel> getActiveChats() {
//        Date date = null;
//        ArrayList<ChatModel> listChats = new ArrayList<>();
////        listChats.addAll(
////                Realm.getDefaultInstance()
////                        .copyFromRealm(RealmManager.getInstance()
////                                .getData(ChatModel.class)
////                                .equalTo("archived", ChatModel.ArchivedState.NOT_ARCHIVED.ordinal())
////                                .notEqualTo("lastActivity", date)
////                                .findAllSorted(new String[]{"chatSettingModel.pinned", "lastActivity"},
////                                        new Sort[]{Sort.DESCENDING, Sort.DESCENDING})));
//
//        listChats.addAll(
//                Realm.getDefaultInstance()
//                        .copyFromRealm(RealmManager.getInstance()
//                                .getData(ChatModel.class)
//                                .equalTo("archived", ChatModel.ArchivedState.NOT_ARCHIVED.ordinal())
//                                .notEqualTo("lastActivity", date)
//                                .beginGroup() /*Group 1 Start*/
//                                .notEqualTo("type", ChatType.GROUP.ordinal())
//                                .or()
//                                .beginGroup() /*Group 2 Start*/
//                                .equalTo("type", ChatType.GROUP.ordinal())
//                                .equalTo("groupDetailModel.isGroupLeave", false)
//                                .endGroup() /*Group 2 End*/
//                                .endGroup()  /*Group 1 End*/
//                                .findAllSorted(new String[]{"chatSettingModel.pinned", "lastActivity"},
//                                        new Sort[]{Sort.DESCENDING, Sort.DESCENDING})));
//
//        return listChats;
//    }
//
//
//    public static ArrayList<ContactDetailModel> getBlockedContacts() {
//        ArrayList<ContactDetailModel> listContacts = new ArrayList<>();
//        listContacts.addAll(
//                Realm.getDefaultInstance().copyFromRealm(
//                        RealmManager.getInstance().getData(ContactDetailModel.class)
//                                .equalTo("status", ContactStatus.getStatus(ContactStatus.BLOCKED))
//                                .findAll()
//                                .sort("status", Sort.DESCENDING, "name", Sort.ASCENDING)
//                ));
//
//        return listContacts;
//    }
//
//    public static ArrayList<ContactDetailModel> getRegisteredContacts() {
//        ArrayList<ContactDetailModel> listContacts = new ArrayList<>();
//        listContacts.addAll(
//                Realm.getDefaultInstance().copyFromRealm(
//                        RealmManager.getInstance().getData(ContactDetailModel.class)
//                                .equalTo("status", ContactStatus.getStatus(ContactStatus.REGISTERED))
//                                .findAll()
//                                .sort("status", Sort.DESCENDING, "name", Sort.ASCENDING)
//                ));
//
//        return listContacts;
//    }
//
//    public static ContactDetailModel getContactById(String phone) {
//        return RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", phone).findFirst();
//    }
//
//    public static ChatSettingModel getChatSettingById(String phone) {
//        return RealmManager.getInstance().getData(ChatSettingModel.class).equalTo("username", phone).findFirst();
//    }
//
//    public static ContactDetailModel addContact(String contactId, String phoneNo, String name, ContactStatus contactStatus) {
//        ContactDetailModel contactDetail = getContactById(phoneNo);
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        if (contactDetail == null) {
//            contactDetail = new ContactDetailModel();
//            contactDetail.setUserName(phoneNo);
//        }
//
//        contactDetail.setContactId(contactId);
//        contactDetail.setName(name);
//        contactDetail.setStatus(contactStatus);
//        realm.insertOrUpdate(contactDetail);
//        realm.commitTransaction();
//        realm.close();
//
//        if (contactStatus == ContactStatus.ANONYMOUS)
//            XMPPManager.getVCard(phoneNo);
//
//        return contactDetail;
//    }
//
//    public static ChatSettingModel addChatSetting(String username) {
//        ChatSettingModel chatSettingModel = getChatSettingById(username);
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        if (chatSettingModel == null) {
//            chatSettingModel = new ChatSettingModel();
//            chatSettingModel.setUsername(username);
//            chatSettingModel.setUse_custom_notifications(0);
//            chatSettingModel.setMessage_tone("content://settings/system/notification_sound");
//            Uri callToneUri = RingtoneManager.getActualDefaultRingtoneUri(BaseApplication.getAppContext(), RingtoneManager.TYPE_RINGTONE);
//            chatSettingModel.setCall_tone(callToneUri != null ? callToneUri.toString() : "");
//        }
//
//        realm.insertOrUpdate(chatSettingModel.isManaged() ? RealmManager.getInstance().getCopy(chatSettingModel) : chatSettingModel);
//        realm.commitTransaction();
//        realm.close();
//        return chatSettingModel;
//    }
//
//    public static ContactDetailModel updateContact(String contactId, String phoneNo, String name) {
//        ContactDetailModel contactDetail = RealmManager.getInstance()
//                .getData(ContactDetailModel.class)
//                .equalTo("UserName", phoneNo + "@" + AppConstants.DOMAIN)
//                .findFirst();
//        if (contactDetail == null)
//            addContact(contactId, phoneNo, name, ContactStatus.PENDING);
//        else {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//            contactDetail.setContactId(contactId);
//            contactDetail.setName(name);
//
//            if (contactDetail.getStatus() == ContactStatus.ANONYMOUS)
//                contactDetail.setStatus(ContactStatus.PENDING);
//
//            realm.insertOrUpdate(contactDetail);
//            realm.commitTransaction();
//            realm.close();
//        }
//
//        return contactDetail;
//    }
//
//    public static void DeleteContact(String phoneNo) {
//        ContactDetailModel contactDetail = RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", phoneNo).findFirst();
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        contactDetail.setContactId("-1");
//        contactDetail.setName(contactDetail.getBareUserName());
//        contactDetail.setStatus(ContactStatus.ANONYMOUS);
//        realm.commitTransaction();
//        realm.close();
//    }
//
//    public static ContactDetailModel updateContactStatus(String phoneNo, ContactStatus contactStatus) {
//        ContactDetailModel contactDetail = RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", phoneNo).findFirst();
//        if (contactDetail != null && contactDetail.getStatus() != contactStatus) {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//            contactDetail.setStatus(contactStatus);
//            realm.insertOrUpdate(contactDetail);
//            realm.commitTransaction();
//            realm.close();
//        }
//
//        return contactDetail;
//    }
//
//    public static ContactDetailModel updateContactRequestStatus(String phoneNo, RequestStatus requestStatus) {
//        ContactDetailModel contactDetail = RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", phoneNo).findFirst();
//        if (contactDetail != null && contactDetail.getRequestStatus() != requestStatus) {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//            contactDetail.setRequestStatus(requestStatus);
//            realm.insertOrUpdate(contactDetail);
//            realm.commitTransaction();
//            realm.close();
//        }
//
//        return contactDetail;
//    }
//
//
//    public static ContactDetailModel updateContactHashKey(String phoneNo, String hashKey) {
//        ContactDetailModel contactDetail = RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", phoneNo).findFirst();
//        if (contactDetail != null) {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//            contactDetail.setHashKeyPhoto(hashKey);
//            realm.insertOrUpdate(contactDetail);
//            realm.commitTransaction();
//            realm.close();
//        }
//
//        return contactDetail;
//    }
//
//
//    public static ContactDetailModel updateUserStatus(String phoneNo, String userStatus) {
//        ContactDetailModel contactDetail = RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", phoneNo).findFirst();
//        if (contactDetail != null) {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//            contactDetail.setUserStatus(userStatus);
//            realm.insertOrUpdate(contactDetail);
//            realm.commitTransaction();
//            realm.close();
//        }
//
//        return contactDetail;
//    }
//
//
//    public static ContactDetailModel updateContact(ContactDetailModel contactDetail) {
//        if (contactDetail != null) {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//            realm.insertOrUpdate(contactDetail);
//            realm.commitTransaction();
//            realm.close();
//        }
//
//        return contactDetail;
//    }
//
//    public static void updateContactVCARD(VCard vCard) {
//        ContactDetailModel contactDetailModel = RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", vCard.getFrom().asBareJid().toString()).findFirst();
//        if (contactDetailModel != null) {
//            String avatar = vCard.getField(AppConstants.FIELD_THUMBNAIL);
//            String userStatus = vCard.getField(AppConstants.FIELD_USER_STATUS);
//            String name = vCard.getFirstName();
//            boolean isAvatarChanged = false;
//            boolean isNameChanged = false;
//            boolean isStatusChanged = false;
//
//            Realm realm = Realm.getDefaultInstance();
//            File userImage = FileManager.getUserImage(contactDetailModel.getAvatar(), true);
//
//            Log.d(TAG, "updateContactVCARD: Old Avatar: " + contactDetailModel.getAvatar());
//            Log.d(TAG, "updateContactVCARD: New Avatar: " + avatar);
//
//            if (userImage != null && avatar != null) {
//                boolean isDeleted = userImage.delete();
//                Log.d(TAG, "userImage: isDeleted userImage Profile Picture: " + isDeleted);
//            }
//
//            if (!realm.isInTransaction()) {
//                realm.beginTransaction();
//            }
//
//            if (avatar != null) {
//                if ((contactDetailModel.getAvatar() == null) || !contactDetailModel.getAvatar().equals(avatar)) {
//                    contactDetailModel.setAvatar(avatar);
//                    isAvatarChanged = true;
//                    if (userImage != null) {
//                        boolean isDeleted = userImage.delete();
//                        Log.d(TAG, "userImage: isDeleted userImage Profile Picture: " + isDeleted);
//                    }
//
//                }
//            } else {
//                contactDetailModel.setAvatar(avatar);
//                isAvatarChanged = true;
//                if (userImage != null) {
//                    boolean isDeleted = userImage.delete();
//                    Log.d(TAG, "userImage: isDeleted userImage Profile Picture: " + isDeleted);
//                }
//            }
//            if (!contactDetailModel.getUserStatus().equals(userStatus)) {
//                contactDetailModel.setUserStatus(userStatus);
//                isStatusChanged = true;
//
//            }
//            if (!contactDetailModel.getName().equals(name)) {
//                contactDetailModel.setOriginalName(name);
//                isNameChanged = true;
//            }
//
//
//            realm.insertOrUpdate(contactDetailModel);
//            Log.d(TAG, "UserModel Status:" + userStatus);
//            realm.commitTransaction();
//            realm.close();
//
//
//            if (isNameChanged) {
//                // FIXME: 9/28/2017 NO Scenario for this yet.
//            }
//            if (isAvatarChanged) {
//                if (avatar == null) {
//                    BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_USER_IMAGE_UPDATE, contactDetailModel.getUserName()));
//                } else {
//                    AmazonS3Manager.getInstance().downloadUserProfileImage(contactDetailModel, true);
//                }
//            }
//            if (isStatusChanged) {
//                BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_STATUS_CHANGED, contactDetailModel.getUserName()));
//            }
//
//        }
//    }
//
//    public static ContactDetailModel setUserProfileImage(String username, File file) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        ContactDetailModel contactDetail = realm.where(ContactDetailModel.class).equalTo("UserName", username).findFirst();
//        if (contactDetail != null)
//            contactDetail.setThumbImage(Helper.encodeFileInBase64(file.getPath()));
//
//        realm.commitTransaction();
//        realm.close();
//
//        return contactDetail;
//    }
//
//    /* Message's Related Helper <Start> */
//    public static ArrayList<MessageModel> getMessages() {
//        ArrayList<MessageModel> listContacts = new ArrayList<>();
//        listContacts.addAll(RealmManager.getInstance().getData(MessageModel.class)
//                .distinct("name").sort("name"));
//        return listContacts;
//    }
//
//    public static ArrayList<MessageModel> getMessages(String jid) {
//        ArrayList<MessageModel> listMessages = new ArrayList<>();
//        listMessages.addAll(RealmManager.getInstance().getData(MessageModel.class).distinct("name").sort("name"));
//        return listMessages;
//    }
//
//
//    public static void deleteParticipantById(int id) {
//        ParticipantModel participantModel = RealmManager.getInstance().getData(ParticipantModel.class).equalTo("id", id).findFirst();
//        if (participantModel == null)
//            return;
//        RealmManager.getInstance().delete(participantModel);
//    }
//
//    public static ArrayList<MessageModel> getMessagesByKeyword(String keyword) {
//        ArrayList<MessageModel> listMessages = new ArrayList<>();
//        listMessages.addAll(RealmManager.getInstance()
//                .getData(MessageModel.class)
//                .equalTo("isDeleted", false)
//                .beginsWith("text", keyword, Case.INSENSITIVE)
//                .or()
//                .contains("text", " " + keyword, Case.INSENSITIVE)
//                .findAll());
//        return listMessages;
//    }
//
//    public static RealmResults<MessageModel> getChatMessagesByKeyword(String keyword, int chatId) {
//        return RealmManager.getInstance().getData(MessageModel.class).equalTo("chatId", chatId)
//                .equalTo("isDeleted", false).contains("text", keyword, Case.INSENSITIVE).findAll();
//    }
//
//    public static RealmResults<MessageModel> getChatMessagesById(int chatId) {
//        return RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("chatId", chatId).equalTo("isDeleted", false)
//                .findAll();
//    }
//
//
//    public static MessageModel getMessage(String messageId) {
//        return RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("id", messageId).findFirst();
//    }
//
//
//    public static MessageModel getMessageByStanzaId(String stanzaId) {
//        return RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("stanzaId", stanzaId).findFirst();
//    }
//
//    /*
//    * For broadcast responseMessage
//    * @param from
//    */
//
//    public static void updateMessageStatus(String msgId, String from, MessageStatus status, boolean notifyToAll) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        MessageModel msg = realm.where(MessageModel.class).equalTo("stanzaId", msgId).findFirst();
//
//        if (msg != null) {
//            if (msg.isBroadcast()) {
//                if (from == null) {
//                    RealmResults<MessageModel> messages = realm.where(MessageModel.class).equalTo("stanzaId", msgId).findAll();
//                    for (int i = 0; i < messages.size(); i++) {
//                        messages.get(i).setStatus(status);
//                    }
//                    realm.commitTransaction();
//                    realm.close();
//                    return;
//                } else
//                    msg = realm.where(MessageModel.class).equalTo("stanzaId", msgId).equalTo("remoteJid", from).findFirst();
//
//            }
//
//            if (status.getValue() > msg.getStatus().getValue()) {
//                msg.setStatus(status);
//                realm.copyToRealmOrUpdate(msg);
//                if (notifyToAll)
//                    BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_MESSAGE_STATE_CHANGED, realm.copyFromRealm(msg)));
//            }
//        }
//        realm.commitTransaction();
//        realm.close();
//    }
//
//    public static void updateMediaTransferStatus(String msgId, TransferState status) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        RealmResults<MessageModel> msgs = realm.where(MessageModel.class).equalTo("stanzaId", msgId).findAll();
//        for (MessageModel msg : msgs) {
//            if (status == TransferState.COMPLETED)
//                msg.setMediaStatus(MediaStatus.SUCCESS);
//            else if (status == TransferState.FAILED) {
//                msg.setMediaStatus(MediaStatus.FAILED);
//            }
//            realm.copyToRealmOrUpdate(msg);
//            BaseApplication.getPublishSubject().onNext(new Pair<>(status == TransferState.COMPLETED ? Events.ON_TRANSFER_COMPLETE : Events.ON_TRANSFER_FAILURE, msg));
//        }
//        realm.commitTransaction();
//        realm.close();
//    }
//
//    public static void setMediaTransferId(MessageModel msg, int Id) {
//        Realm realm = openRealmInstance();
//
//        msg.setMediaTransferId(Id);
//        realm.insertOrUpdate(msg);
//
//        closeRealmInstance(realm);
//    }
//
//
//    public static MessageModel updateMediaTransferStatus(MessageModel msg, MediaStatus status) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        if (msg != null) {
//            msg.setMediaStatus(status);
//            realm.copyToRealmOrUpdate(msg);
//        }
//        realm.commitTransaction();
//        realm.close();
//
//        return msg;
//    }
//
//    public static void markAllMessagesSeen(Integer chatId, boolean myMessages) {
//        // TODO: 9/5/2017 mark only those responseMessage as seen which has delivered
//        RealmResults<MessageModel> messages = RealmManager.getInstance().getData(MessageModel.class).equalTo("isMyMessage", myMessages).equalTo("chatId", chatId).equalTo("status", MessageStatus.DELIVERED.getValue()).findAll();
//        if (messages.size() > 0) {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            for (MessageModel item : messages) {
//                item.setStatus(MessageStatus.SEEN);
//                realm.copyToRealmOrUpdate(item);
//                BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_MESSAGE_STATE_CHANGED, realm.copyFromRealm(item)));
//            }
//            realm.commitTransaction();
//            realm.close();
//        }
//    }
//
//
//    public static void markAllGroupMessagesSeen(Integer groupId, String memberId) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        RealmResults<GroupMessageInfo> groupMessageInfo = realm.where(GroupMessageInfo.class)
//
//                .equalTo("chatId", groupId)
//                .equalTo("memberId", memberId)
//                .equalTo("status", MessageStatus.DELIVERED.getValue())
//                .findAll();
//
//        for (GroupMessageInfo item : groupMessageInfo) {
//            item.setStatus(MessageStatus.SEEN);
//            realm.insertOrUpdate(groupMessageInfo);
////            BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_MESSAGE_STATE_CHANGED, realm.copyFromRealm(groupMessageInfo)));
//        }
//
//        realm.commitTransaction();
//        realm.close();
//    }
//
//
//    public static void markAllMessagesDelivered(String username, Date lastMessageTime) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        RealmResults<MessageModel> messages = realm.where(MessageModel.class).equalTo("chatId", getChatOfUser(username).getId()).equalTo("status", MessageStatus.SENT.getValue()).lessThan("date", lastMessageTime).findAll();
//        for (MessageModel item : messages) {
//            if (item != null && MessageStatus.DELIVERED.getValue() > item.getStatus().getValue()) {
//                item.setStatus(status);
//                realm.copyToRealmOrUpdate(item);
//            }
//            BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_MESSAGE_STATE_CHANGED, realm.copyFromRealm(item)));
//        }
//
//        realm.commitTransaction();
//        realm.close();
//
//    }
//
//    public static void updateGroupMessageStatus(Integer chatId, String msgId, MessageStatus status, String memberId) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        GroupMessageInfo msgStatus = realm.where(GroupMessageInfo.class)
//                .equalTo("msgId", msgId)
//                .equalTo("memberId", memberId)
//                .findFirst();
//
//        if (msgStatus == null) {
//            msgStatus = new GroupMessageInfo();
//            msgStatus.setStatus(status);
//            msgStatus.setMsgId(msgId);
//            msgStatus.setChatId(chatId);
//            msgStatus.setMemberId(memberId);
//
//        } else {
//            if (status.getValue() < msgStatus.getStatus().getValue())
//                return;
//            else
//                msgStatus.setStatus(status);
//        }
//
//        realm.insertOrUpdate(msgStatus);
//        realm.commitTransaction();
//        realm.close();
//    }
//
//    public static ChatType getChatType(String username) {
//        return RealmManager.getInstance().getData(ChatModel.class).equalTo("username", username).findFirst().getType();
//    }
//
//    public static long getUnseenCount(Integer chatId) {
//        return RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("chatId", chatId)
//                .equalTo("isMyMessage", false)
//                .notEqualTo("status", MessageStatus.SEEN.getValue())
//                .count();
//    }
//
//    public static long getAllUnseenCount() {
//
//        /*Getting All The Messages , We cannot identify directly if responseMessage is of Broadcast, so taking a result instead */
//        RealmResults<MessageModel> realmResults = RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("isMyMessage", false)
//                .notEqualTo("status", MessageStatus.SEEN.getValue())
//                .distinct("chatId");
//
//        /*We have to eliminate all the messages or more precisely info messages if is it of Broadcast,
//        * So performing query only for the Broadcasts*/
//        int unseenChatsExceptBroadCast = 0;
//        for (int i = 0; i < realmResults.size(); i++) {
//            if (LocalDB.getChatById(realmResults.get(i).getChatId()).getType() != ChatType.BROAD_CAST)
//                unseenChatsExceptBroadCast++;
//        }
//
//        return unseenChatsExceptBroadCast;
//    }
//
//    public static long getMessageCount(Integer chatId) {
//        return RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("chatId", chatId)
//                .count();
//    }
//
//    public static Pair<RealmResults<MessageModel>, RealmResults<ChatModel>> getMessagesWithChats(FilterType filterType, MessageStatus messageStatus) {
//        RealmQuery<MessageModel> unseenMessages = RealmManager.getInstance()
//                .getData(MessageModel.class)
//                .equalTo("status", messageStatus.getValue());
//        if (filterType != FilterType.ALL)
//            unseenMessages = unseenMessages.equalTo("isMyMessage", filterType == FilterType.SEND);
//
//        RealmResults<MessageModel> results = unseenMessages.distinct("chatId");
//        Integer[] chatIds = new Integer[results.size()];
//        for (int i = 0; i < results.size(); i++) {
//            chatIds[i] = results.get(i).getChatId();
//        }
//
//        return new Pair<>(unseenMessages.findAll(), chatIds.length > 0 ? RealmManager.getInstance().getData(ChatModel.class).in("id", chatIds).findAll() : null);
//    }
//
//    public static Pair<RealmResults<MessageModel>, RealmResults<ChatModel>> getAllUnSeenMessages() {
//        RealmQuery<MessageModel> unseenMessages = RealmManager.getInstance()
//                .getData(MessageModel.class)
//                .equalTo("isMyMessage", false)
//                .notEqualTo("status", MessageStatus.SEEN.getValue());
//
//        if (unseenMessages.findAll().size() <= 0)
//            return null;
//
//        RealmResults<MessageModel> results = unseenMessages.distinct("chatId")
//                .sort("date", Sort.DESCENDING);
//
//        List<Integer> chatIdsList = new ArrayList<>();
//        for (int i = 0; i < results.size(); i++) {
//            ChatModel chatModel = LocalDB.getChatById(results.get(i).getChatId());
//            if (chatModel.getType() == ChatType.BROAD_CAST)
//                continue;
//
//            ChatSettingModel chatSettingModel = LocalDB.getChatById(results.get(i).getChatId()).getChatSettingModel();
//            MuteType muteType = chatSettingModel.getMute_notifications_Type();
//            if (muteType != MuteType.OFF) { // Means Sound will not come only
//                if ((chatSettingModel.getMute_end() - DateManager.getCurrentMillis()) > 0) { // Means mute Duration is not end yet
//                    if (chatSettingModel.isShow_notification()) { //In WhatsApp If its true, then Heads-up Notifications will not display , notification just stay at top without coming on screen
//                        chatIdsList.add(results.get(i).getChatId());
//                    }
//                    /* We know that the Chatlist is sorted : DESCENDING ,
//                    *  means the last/recent item come first So we are checking the
//                    *  MUTE STATUS of this CHat and identify the behaviour of remaining chats,
//                    *  As isShow_notification is False , So passing NULL from here indicates
//                    *  we don't want to generate Notification
//                    **/
//                    else if (i == 0) {
//                        return null;
//                    }
//
//                    continue; //No Need to Add when isShow_notification is False
//                }
//                //Chats whose setted duration are over but still mark as mute
//                chatIdsList.add(results.get(i).getChatId());
//            } else {
//                //Need to Add chats that are not mute
//                chatIdsList.add(results.get(i).getChatId());
//            }
//        }
//
//        /*RealM Hack, as empty array should not be passed to in*/
//        if (chatIdsList.size() == 0)
//            chatIdsList.add(-1);
//
//        return new Pair<>(unseenMessages
//                .in("chatId", chatIdsList.toArray(new Integer[chatIdsList.size()]))
//                .findAllSorted("date", Sort.ASCENDING),
//                RealmManager.getInstance()
//                        .getData(ChatModel.class)
//                        .in("id", chatIdsList.toArray(new Integer[chatIdsList.size()]))
//                        .findAllSorted("lastActivity", Sort.ASCENDING)
//        );
//    }
//
//
//    public static MessageModel saveInfoMessages(String text, ChatModel chatModel, boolean requireMessageCallBack) {
//        chatModel = chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel;
//        MessageModel messageData = new MessageModel();
//        messageData.setId();
//        messageData.setStanzaId(UUID.randomUUID().toString());
//        messageData.setText(text);
//        messageData.setChatId(chatModel.getId());
//        messageData.setDate(DateManager.getCurrentMillis());
//        messageData.setMessageType(MessageType.INFO);
//        messageData.setStatus(MessageStatus.DELIVERED);
//
//        chatModel.setLastMessageId(messageData.getId());
//        chatModel.setLastActivity(messageData.getDate());
//
//        RealmManager.getInstance().insertOrUpdate(messageData);
//
//        RealmManager.getInstance().insertOrUpdate(chatModel);
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel));
//        if (requireMessageCallBack)
//            BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_MESSAGE, messageData.isManaged() ? RealmManager.getInstance().getCopy(messageData) : messageData));
//
//        return messageData;
//    }
//
//    public static void saveMessage(MessageModel msg, ChatModel chatModel) {
//        msg.setChatId(chatModel.getId());
//        if (RealmManager.getInstance().getData(MessageModel.class).equalTo("id", msg.getId()).findFirst() == null) {
//            if (!msg.isMyMessage()) {
//                if (msg.isMedia()) {
//                    File localFile = FileManager.getFile(MediaType.MEDIA_IMAGE);
//                    msg.setMediaLocalPath(localFile.getAbsolutePath());
//                    // TODO : If user on 3G/4G and they disable downloading over mobile data then following block will not run
//                    {
//                        msg.setMediaStatus(MediaStatus.TRANSFERRING);
//                        RealmManager.getInstance().insertData(msg);
//                        AmazonS3Manager.getInstance().downloadMediaFile(msg);
//                    }
//                } else {
//                    if (msg.getMessageType() == MessageType.LOCATION) {
//                        msg.setMediaStatus(MediaStatus.SUCCESS);
//                    }
//                    RealmManager.getInstance().insertData(msg);
//                }
//
//            } else {
//                RealmManager.getInstance().insertData(msg);
//                if (chatModel.getType() == ChatType.GROUP) {
//                    for (ParticipantModel item : chatModel.getGroupDetailModel().getParticipantModels()) {
//                        LocalDB.updateGroupMessageStatus(chatModel.getId(), msg.getId(), MessageStatus.PENDING, item.getContactDetailModel().getUserName());
//                    }
//                }
//            }
//
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//            chatModel.setLastMessageId(msg.getId());
//            chatModel.setLastActivity(msg.getDate());
//            if (chatModel.getArchived() == ChatModel.ArchivedState.ARCHIVED) {
//                chatModel.setArchived(ChatModel.ArchivedState.NOT_ARCHIVED);
//            }
//            realm.insertOrUpdate(chatModel);
//            realm.commitTransaction();
//            realm.close();
//            BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel));
//
//        }
//    }
//
//    public static void clearChat(ChatModel chatModel, boolean keepStarred) {
//        RealmQuery<MessageModel> query = RealmManager.getInstance()
//                .getData(MessageModel.class)
//                .equalTo("chatId", chatModel.getId());
//
//        if (keepStarred)
//            query.equalTo("isStarred", false);
//
//        boolean deleteStatus = RealmManager.getInstance()
//                .delete(query.findAll());
//
//        if (keepStarred) //Removing All the Messages , so need to setLastActivity to NULL
//        {
//            MessageModel messageModel = RealmManager.getInstance()
//                    .getData(MessageModel.class)
//                    .equalTo("chatId", chatModel.getId())
//                    .findFirst();
//
//            if (messageModel != null) { // In some cases it can be NULL
//                chatModel.setLastActivity(messageModel.getDate());
//                chatModel.setLastMessageId(messageModel.getId());
//            }
//
//        }
//
//        updateChatModel(chatModel);
//
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CONVERSATION_MODIFIED, deleteStatus));
//
//    }
//
//    public static ChatModel getChatOfUserInTransaction(String username) {
//        // // FIXME: 8/29/2017
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        ChatModel chatModel = RealmManager.getInstance().getData(ChatModel.class).equalTo("username", username).findFirst();
//
//        if (chatModel == null) // When anonymous person sent responseMessage
//        {
//            createChat(username, ChatType.SINGLE);
//            chatModel = RealmManager.getInstance().getData(ChatModel.class).equalTo("username", username).findFirst();
//        }
//        return chatModel;
//    }
//
//    public static ChatModel getChatOfUser(String username) {
//        // // FIXME: 8/29/2017
//        ChatModel chatModel = RealmManager.getInstance().getData(ChatModel.class).equalTo("username", username).findFirst();
//
//        if (chatModel == null) // When anonymous person sent responseMessage
//        {
//            createChat(username, ChatType.SINGLE);
//            chatModel = RealmManager.getInstance().getData(ChatModel.class).equalTo("username", username).findFirst();
//        }
//
//        return chatModel;
//    }
//
//    public static ChatModel getGroupChatOfUser(String username) {
//        // // FIXME: 8/29/2017
//        ChatModel chatModel = RealmManager.getInstance().getData(ChatModel.class).equalTo("username", username).findFirst();
//
//        if (chatModel == null) // When anonymous person sent responseMessage
//        {
//            createChat(username, ChatType.GROUP);
//            chatModel = RealmManager.getInstance().getData(ChatModel.class).equalTo("username", username).findFirst();
//        }
//
//        return chatModel;
//    }
//
//    public static ChatModel getChatById(Integer Id) {
//        return RealmManager.getInstance()
//                .getData(ChatModel.class)
//                .equalTo("id", Id)
//                .findFirst();
//    }
//
//    public static ArrayList<GroupMessageInfo> getGroupMessageInfoByStatus(String msgId, MessageStatus status) {
//        ArrayList<GroupMessageInfo> messageInfos = new ArrayList<>();
//        messageInfos.addAll(RealmManager.getInstance()
//                .getData(GroupMessageInfo.class)
//                .equalTo("msgId", msgId)
//                .equalTo("status", status.getValue())
//                .findAll());
//        return messageInfos;
//    }
//
//    public static List<ParticipantModel> getParticipantsById(String groupId) {
//        return RealmManager.getInstance()
//                .getCopy(RealmManager.getInstance()
//                        .getData(GroupDetailModel.class)
//                        .equalTo("chatId", groupId)
//                        .findFirst())
//                .getParticipantModels();
//
////        return RealmManager.getInstance()
////                .getCopy(RealmManager.getInstance()
////                        .getData(GroupParticipants.class)
////                        .equalTo("groupId", groupId).findFirst()).getParticipants();
//    }
//
//    /* Message's Related Helper <End> */
//
//    public static void createChat(String Jid, boolean createOnMainThread) {
//        if (!isChatExist(Jid)) {
//            final ChatModel chatModel = new ChatModel();
//            chatModel.setUsername(Jid);
//            if (createOnMainThread) {
////                XMPPManager.getInstance().getVCard(Jid, new OnVCardLoaded()
////                {
////                    @Override
////                    public void loaded(VCard vCard)
////                    {
////                        chatModel.setDisplayName(vCard.getFirstName() + " " + ((vCard.getLastName() == null || vCard.getLastName().equals("")) ? "" : vCard.getLastName()));
////                        chatModel.setAvatar(vCard.getField(AppConstants.FIELD_THUMBNAIL));
////                        RealmManager.getInstance().insertOrUpdate(chatModel);
////                        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CHAT_CREATED, chatModel));
////                    }
////                });
//
//            } else {
////                VCard vCard = XMPPManager.getInstance().getVCard(Jid);
////                chatModel.setDisplayName(vCard.getFirstName() + " " + ((vCard.getLastName() == null || vCard.getLastName().equals("")) ? "" : vCard.getLastName()));
////                chatModel.setAvatar(vCard.getField(AppConstants.FIELD_THUMBNAIL));
////                RealmManager.getInstance().insertOrUpdate(chatModel);
////                BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CHAT_CREATED, chatModel));
//            }
//        }
//    }
//
//    /*public static ChatModel createChat(String JID)
//    {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        int nextID = getNextKey(realm, ChatModel.class, "id");
//        final ChatModel chatModel = new ChatModel();
//        chatModel.setId(nextID);
//        chatModel.setUsername(JID);
//        chatModel.setType(ChatType.SINGLE);
//        ContactDetailModel contactDetail = LocalDB.getContactById(JID);
//        if (contactDetail == null) //-1 b/c contact not exist , so passing -1 here
//            contactDetail = addContact("-1" , JID, JID.replaceAll("@" + AppConstants.DOMAIN, ""), ContactStatus.ANONYMOUS);
//        chatModel.setContactDetail(contactDetail);
//
//        ChatSettingModel chatSettingModel = LocalDB.getChatSettingById(JID);
//        if (chatSettingModel == null)
//            chatSettingModel = addChatSetting(JID);
//
//        chatModel.setChatSettingModel(chatSettingModel);
//
//        RealmManager.getInstance().insertOrUpdate(chatModel);
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CHAT_CREATED, chatModel));
//        return chatModel;
//    }*/
//
//    // isGroupCreatingForFirstTime : If chat is already created then fire ON_CHAT_UPDATED Event, if not then ON_NEW_CHAT_CREATED
//    public static ChatModel createGroupChat(String Jid, String subject, String avatar, ArrayList<ParticipantModel> participants, boolean isGroupCreatingForFirstTime) {
//        Realm realm = getDefaultInstance();
//        ChatModel chatModel = getChat(Jid, false);
//
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        if (chatModel == null) {
//            chatModel = new ChatModel();
//            int nextID = getNextKey(realm, ChatModel.class, "id");
//            chatModel.setId(nextID);
//            chatModel.setUsername(Jid);
//            chatModel.setDisplayname(subject);
//            chatModel.setType(ChatType.GROUP);
//        }
//
//        ChatSettingModel chatSettingModel = LocalDB.getChatSettingById(Jid);
//        if (chatSettingModel == null)
//            chatSettingModel = addChatSetting(Jid);
//
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        chatModel.setChatSettingModel(chatSettingModel);
//
//        GroupDetailModel groupDetailModel = chatModel.getGroupDetailModel();
//        if (groupDetailModel == null) {
//            groupDetailModel = createParticipantModel(participants, subject != null ? subject : chatModel.getDisplayname(), avatar);
//        } else {
//            groupDetailModel = updateParticipantModel(groupDetailModel, participants, subject);
//        }
//
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        groupDetailModel.setGroupLeave(false);
//        chatModel.setGroupDetailModel(groupDetailModel);
//        realm.commitTransaction();
//        realm.close();
//
//        RealmManager.getInstance().insertOrUpdate(chatModel);
//
//        // When Passing ChatModel We Dont need GroupDetail Managed Object , So Resetting it with un Managed Copy
//        chatModel = chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel;
//        chatModel.setGroupDetailModel(groupDetailModel.isManaged() ? RealmManager.getInstance().getCopy(groupDetailModel) : groupDetailModel);
//
//        BaseApplication.getPublishSubject().onNext(new Pair<>(isGroupCreatingForFirstTime ? Events.ON_NEW_CHAT_CREATED : Events.ON_CHAT_UPDATED, chatModel));
////        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CHAT_CREATED, chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel));
//
//        return chatModel;
//    }
//
//    public static GroupDetailModel createParticipantModel(ArrayList<ParticipantModel> participants, String subject, String avatar) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        GroupDetailModel groupDetailModel = new GroupDetailModel();
//        groupDetailModel.setId(getNextKey(realm, GroupDetailModel.class, "id"));
//        if (subject != null)
//            groupDetailModel.setChatName(subject);
//        groupDetailModel.setCreatedTime(Calendar.getInstance().getTimeInMillis());
//        if (avatar != null)
//            groupDetailModel.setAvatar(avatar);
//        if (participants != null) {
//            RealmList<ParticipantModel> realmList = new RealmList<>();
//            for (int i = 0; i < participants.size(); i++) {
//                realmList.add(participants.get(i));
//            }
//            groupDetailModel.setParticipantModels(realmList);
//        }
//
//        realm.insertOrUpdate(groupDetailModel);
//        realm.commitTransaction();
//        realm.close();
//
//        groupDetailModel = RealmManager.getInstance().getData(GroupDetailModel.class).equalTo("id", groupDetailModel.getId()).findFirst();
//        return groupDetailModel;
//    }
//
//    public static GroupDetailModel updateParticipantModel(GroupDetailModel groupDetailModel, ArrayList<ParticipantModel> participants, String subject) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        groupDetailModel = groupDetailModel.isManaged() ? RealmManager.getInstance().getCopy(groupDetailModel) : groupDetailModel;
//        if (subject != null)
//            groupDetailModel.setChatName(subject);
//        if (participants != null) {
//            RealmList<ParticipantModel> realmList = new RealmList<>();
//            for (int i = 0; i < participants.size(); i++) {
//                realmList.add(participants.get(i));
//            }
//            groupDetailModel.setParticipantModels(realmList);
//        }
//
//        realm.insertOrUpdate(groupDetailModel);
//        realm.commitTransaction();
//        realm.close();
//
//        groupDetailModel = RealmManager.getInstance().getData(GroupDetailModel.class).equalTo("id", groupDetailModel.getId()).findFirst();
//        return groupDetailModel;
//
//    }
//
//    public static GroupDetailModel saveGroupMembers(ChatModel chatModel, ArrayList<ParticipantModel> participants, boolean isAdmin) {
//        int nextID = getNextKey(Realm.getDefaultInstance(), GroupDetailModel.class, "id");
//        final GroupDetailModel groupDetailModel = new GroupDetailModel();
//
//        groupDetailModel.setId(nextID);
//        groupDetailModel.setChatId(chatModel.getId());
//        groupDetailModel.setChatName(chatModel.getDisplayname());
//        groupDetailModel.setCreatedTime(Calendar.getInstance().getTimeInMillis());
//        RealmList<ParticipantModel> realmList = new RealmList<>();
//        for (int i = 0; i < participants.size(); i++) {
//            realmList.add(participants.get(i));
//        }
//        groupDetailModel.setParticipantModels(realmList);
//        RealmManager.getInstance().insertOrUpdate(groupDetailModel);
//        RealmManager.getInstance().insertOrUpdate(chatModel);
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CHAT_CREATED, chatModel));
//        return groupDetailModel;
////        GroupParticipants groupParticipants = RealmManager.getInstance()
////                .getData(GroupParticipants.class)
////                .equalTo("groupId", groupId)
////                .findFirst();
////        if (groupParticipants == null)
////        {
////            groupParticipants = new GroupParticipants();
////            groupParticipants.setGroupId(groupId);
////            groupParticipants.setParticipants(participants);
////            RealmManager.getInstance().insertOrUpdate(groupParticipants);
////        } else
////        {
////            Realm realm = Realm.getDefaultInstance();
////            if (!realm.isInTransaction())
////                realm.beginTransaction();
////            groupParticipants.getParticipants().addAll(participants);
////            realm.commitTransaction();
////            realm.close();
////        }
//    }
//
//    public static void updateChatModel(ChatModel chatModel) {
//        Realm realm = getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        realm.copyToRealmOrUpdate(chatModel);
//        realm.commitTransaction();
//        realm.close();
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel));
//    }
//
//    public static void updateParticipant(ParticipantModel participantModel) {
//        Realm realm = getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        realm.copyToRealmOrUpdate(participantModel);
//        realm.commitTransaction();
//        realm.close();
//    }
//
//    public static void updateGroupModel(GroupDetailModel groupDetailModel) {
//        Realm realm = getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        realm.copyToRealmOrUpdate(groupDetailModel);
//        realm.commitTransaction();
//        realm.close();
//    }
//
//    public static void updateMessageModel(MessageModel messageModel) {
//        Realm realm = getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        realm.copyToRealmOrUpdate(messageModel);
//        realm.commitTransaction();
//        realm.close();
//
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, messageModel.isManaged() ? RealmManager.getInstance().getCopy(messageModel) : messageModel));
//    }
//
//
//    public static void deleteMessageModel(String[] ids) {
//        RealmResults<MessageModel> list = RealmManager.getInstance().getData(MessageModel.class).in("id", ids).findAll();
//
//        Realm realm = getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).setDeleted(true);
//        }
//        realm.insertOrUpdate(list);
//        realm.commitTransaction();
//        realm.close();
//
//        MessageModel messageModel = list.get(list.size() - 1);
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, messageModel.isManaged() ? RealmManager.getInstance().getCopy(messageModel) : messageModel));
//    }
//
//    public static RealmResults<MessageModel> starMessageModel(String[] ids, boolean setStar) {
//        RealmResults<MessageModel> list = RealmManager.getInstance().getData(MessageModel.class).in("id", ids).findAllSorted("date", Sort.ASCENDING);
//        Realm realm = getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).setStarred(setStar);
//        }
//
//        realm.insertOrUpdate(list);
//        realm.commitTransaction();
//        realm.close();
//
//        MessageModel messageModel = list.get(list.size() - 1);
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, messageModel.isManaged() ? RealmManager.getInstance().getCopy(messageModel) : messageModel));
//        return list;
//    }
//
//
//    public static GroupDetailModel updateGroup(GroupDetailModel groupDetailModel, String name) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        realm.insertOrUpdate(groupDetailModel);
//        ChatModel chatModel = RealmManager.getInstance().getData(ChatModel.class)
//                .equalTo("id", groupDetailModel.getChatId()).findFirst();
//
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        if (name != null)
//            chatModel.setDisplayname(name);
//        realm.commitTransaction();
//        realm.close();
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, RealmManager.getInstance().getCopy(chatModel)));
//
//
//        return groupDetailModel;
//    }
//
//    public static ChatModel changeSubjectOfGroup(String subject, String avatar, String Jid) {
//        Realm realm = getDefaultInstance();
//        ChatModel chatModel = getChat(Jid, false);
//        if (!realm.isInTransaction()) realm.beginTransaction();
////        chatModel.setType(ChatType.GROUP);
//        chatModel.setDisplayname(subject);
//        GroupDetailModel groupDetailModel = chatModel.getGroupDetailModel();
//
//        if (groupDetailModel == null)
//            groupDetailModel = createParticipantModel(null, subject, avatar);
//        else
//            groupDetailModel = updateParticipantModel(groupDetailModel, null, subject);
//
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        chatModel.setGroupDetailModel(groupDetailModel);
//
//        realm.copyToRealmOrUpdate(chatModel);
//        realm.commitTransaction();
//        realm.close();
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel));
//        return chatModel.isManaged() ? RealmManager.getInstance().getCopy(chatModel) : chatModel;
//
//    }
//
//    public static boolean isChatExist(String Jid) {
//        return RealmManager.getInstance().getData(ChatModel.class).equalTo("username", Jid).findFirst() != null;
//    }
//
//    public static ChatModel getChat(String Jid, boolean copy) {
//        ChatModel chatModel = RealmManager.getInstance().getData(ChatModel.class).equalTo("username", Jid).findFirst();
//        if (chatModel == null)
//            return null;
//
//        if (copy)
//            return RealmManager.getInstance().getCopy(RealmManager.getInstance().getData(ChatModel.class).equalTo("username", Jid).findFirst());
//
//        return chatModel;
//    }
//
//    public static String getNameByUsername(String username) {
//        ContactDetailModel contactDetailModel = RealmManager.getInstance().getCopy(RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", username).findFirst());
//        return contactDetailModel == null ? "" : contactDetailModel.getName();
//    }
//
//    public static ContactDetailModel getContactModelByUsername(String username, boolean isCopy) {
//        ContactDetailModel contactDetailModel = RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", username).findFirst();
//        if (contactDetailModel == null)
//            return null;
//
//        if (isCopy)
//            return RealmManager.getInstance().getCopy(RealmManager.getInstance().getData(ContactDetailModel.class).equalTo("UserName", username).findFirst());
//
//        return contactDetailModel;
//    }
//
//    /* Stanza Related Methods */
//    public static void saveStanza(Stanza stanza, boolean autoSend) {
//        StanzaModel stanzaModel = new StanzaModel();
//        stanzaModel.setId(stanza.getStanzaId());
//        stanzaModel.setStanza(stanza.toXML().toString());
//        stanzaModel.setStatus(StanzaStatus.PENDING);
//        stanzaModel.setAutoSend(autoSend);
//        RealmManager.getInstance().insertOrUpdate(stanzaModel);
//    }
//
//    public static void resetTransferMedia() {
//        RealmResults<MessageModel> messages = RealmManager.getInstance().getData(MessageModel.class).equalTo("mediaStatus", MediaStatus.TRANSFERRING.getValue()).findAll();
//        if (messages.size() > 0) {
//            Realm realm = Realm.getDefaultInstance();
//            if (!realm.isInTransaction()) realm.beginTransaction();
//
//            for (MessageModel item : messages) {
//                item.setMediaStatus(MediaStatus.FAILED);
//                realm.copyToRealmOrUpdate(item);
//            }
//
//            realm.commitTransaction();
//            realm.close();
//        }
//    }
//
//    public static void deleteStanza(String Id) {
//        StanzaModel stanzaModel = RealmManager.getInstance().getData(StanzaModel.class).equalTo("Id", Id).findFirst();
//        if (stanzaModel == null)
//            return;
//        RealmManager.getInstance().delete(stanzaModel);
//    }
//
//    public static void deletePendingStanzas() {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        realm.where(StanzaModel.class).equalTo("autoSend", true).findAll().deleteAllFromRealm();
//        realm.commitTransaction();
//        realm.close();
//    }
//
//    public static StanzaModel getStanzaById(String Id) {
//        return RealmManager.getInstance().getData(StanzaModel.class).equalTo("Id", Id).findFirst();
//    }
//
//    public static ArrayList<StanzaModel> getStanzas(StanzaStatus status) {
//        ArrayList<StanzaModel> listStanzas = new ArrayList<>();
//        listStanzas.addAll(RealmManager.getInstance().getData(StanzaModel.class)
//                .equalTo("status", status.ordinal())
//                .equalTo("autoSend", true)
//                .findAll()
//        );
//        return listStanzas;
//    }
//
//    public static ChatSettingModel updateChatSetting(ChatSettingModel chatSettingModel) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        realm.insertOrUpdate(chatSettingModel);
//        realm.commitTransaction();
//        realm.close();
//        return chatSettingModel;
//    }
//
//    public static ChatSettingModel getSettingByUserName(String userName) {
//        return RealmManager.getInstance()
//                .getData(ChatSettingModel.class)
//                .equalTo("username", userName)
//                .findFirst();
//    }
//
//    public static ArrayList<ChatSettingModel> getAllSetting() {
//        ArrayList<ChatSettingModel> listContacts = new ArrayList<>();
//        listContacts.addAll(
//                Realm.getDefaultInstance().copyFromRealm(
//                        RealmManager.getInstance().getData(ChatSettingModel.class).findAll()
//                ));
//
//        return listContacts;
//    }
//
//    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public static CallLogModel addCallLog(ContactDetailModel contactDetailModel, String time, int duration, CallStatus callStatus, CallType callType) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//
//        int nextID = getNextKey(realm, CallLogModel.class, "id");
//
//        CallLogModel callLogModel = new CallLogModel();
//        callLogModel.setId(nextID);
////        callLogModel.setTo(contactDetailModel);
//        callLogModel.setTo(contactDetailModel.isManaged() ? RealmManager.getInstance().getCopy(contactDetailModel) : contactDetailModel);
//        callLogModel.setTime(time);
//        callLogModel.setDuration(duration);
//        callLogModel.setCallStatus(callStatus);
//        callLogModel.setCallType(callType);
//
//        realm.insertOrUpdate(callLogModel);
//        realm.commitTransaction();
//        realm.close();
//
//        if (callStatus == CallStatus.MISSED) {
//            NotificationsManager.showMissedCallNotification(contactDetailModel.getUserName(), callType);
//            saveInfoMessages(AppConstants.MISCALL_PREFIX + "Missed " + ((callType == CallType.VOICE_CALL) ? "voice call" : "video call"), RealmManager.getInstance().getCopy(LocalDB.getChatOfUser(contactDetailModel.getUserName())), true);
//        }
//
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CALL_LOG, callLogModel.isManaged() ? RealmManager.getInstance().getCopy(callLogModel) : callLogModel));
//
//
//        return callLogModel;
//    }
//
//    public static int getNextKey(Realm realm, Class<? extends RealmModel> realmModel, String fieldName) {
//        int val = 0;
//        Number number = null;
//        try {
//            if (realm == null) {
//                realm = Realm.getDefaultInstance();
////                if (!realm.isInTransaction())
//                {
////                    realm.beginTransaction();
////                    number = realm.where(realmModel).max(fieldName);
////                    realm.commitTransaction();
//                }
//                number = realm.where(realmModel).max(fieldName);
//
//
//            } else
//                number = realm.where(realmModel).max(fieldName);
//            if (number != null) {
//                val = number.intValue() + 1;
//            }
//        } catch (ArrayIndexOutOfBoundsException e) {
//        }
//        return val;
//    }
//
//    public static int getParticipantLastCount() {
//        return getNextKey(null, ParticipantModel.class, "id");
//    }
//
//    public static boolean deleteCallLogs(ArrayList<CallLogModel> callLogs) {
//
//        boolean allLogsDeleted = true;
//        for (int i = 0; i < callLogs.size(); i++) {
//            boolean deleteStatus = RealmManager.getInstance().
//                    delete(RealmManager.getInstance().getData(CallLogModel.class)
//                            .equalTo("id", callLogs.get(i).getId())
//                            .findAll()
//                    );
//            if (!deleteStatus)
//                allLogsDeleted = false;
//        }
//
//        return allLogsDeleted;
//    }
//
//    /*Broadcast Work*/
//
//    public static RecipientModel createBroadcast(ChatModel chatModel, ArrayList<ParticipantModel> participantModels) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//
//        int nextID = getNextKey(realm, RecipientModel.class, "id");
//        final RecipientModel recipientModel = new RecipientModel();
//
//        recipientModel.setId(nextID);
//        recipientModel.setChatId(chatModel.getId());
//        recipientModel.setChatName(chatModel.getDisplayname());
////        recipientModel.setChatName(String.format(Locale.US, BaseApplication.getAppContext().getString(R.string.d_recipients), contactDetailModels.size()));
//        recipientModel.setCreatedTime(Calendar.getInstance().getTimeInMillis());
//        RealmList<ParticipantModel> realmList = new RealmList<>();
//        for (int i = 0; i < participantModels.size(); i++) {
//            realmList.add(participantModels.get(i));
//        }
//        recipientModel.setParticipantModels(realmList);
//        RealmManager.getInstance().insertOrUpdate(recipientModel);
//        RealmManager.getInstance().insertOrUpdate(chatModel);
////        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CHAT_CREATED, chatModel));
//        return recipientModel;
//    }
//
//    public static RecipientModel updateBroadcast(RecipientModel recipientModel, String chatName) {
//
//        RealmManager.getInstance().insertOrUpdate(recipientModel);
//        ChatModel chatModel = RealmManager.getInstance()
//                .getData(ChatModel.class)
//                .equalTo("id", recipientModel.getChatId())
//                .findFirst();
//
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        if (chatName != null)
//            chatModel.setDisplayname(chatName);
//        realm.commitTransaction();
//        realm.close();
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_UPDATED, RealmManager.getInstance().getCopy(chatModel)));
//        return recipientModel;
//    }
//
//    public static ChatModel createChat(String JID, ChatType chatType) {
//        Realm realm = Realm.getDefaultInstance();
//        if (!realm.isInTransaction()) realm.beginTransaction();
//        int nextID = getNextKey(realm, ChatModel.class, "id");
//        // ToDO : check if already exists
//        final ChatModel chatModel = new ChatModel();
//        chatModel.setId(nextID);
//        chatModel.setType(chatType);
//
//        switch (chatType) {
//            case SINGLE:
//                /* WE NEED TO CREATE CHAT FOR SINGLE USERS ONLY , NO NEED FOR GROUP OR BROADCAST*/
//                chatModel.setUsername(JID);
//                ContactDetailModel contactDetail = LocalDB.getContactById(JID);
//                if (contactDetail == null) { //-1 b/c contact not exist , so passing -1 here
//                    contactDetail = addContact("-1", JID, JID.replaceAll("@" + AppConstants.DOMAIN, ""), ContactStatus.ANONYMOUS);
//                }
//                chatModel.setContactDetail(contactDetail.isManaged() ? RealmManager.getInstance().getCopy(contactDetail) : contactDetail);
//
//                break;
//            case BROAD_CAST:
////                chatModel.setUsername(JID);
//                /*Not Passing DisplayName , b/c DisplayName will be setted only whem users Update Group Name*/
//                chatModel.setDisplayname(JID);
//                break;
//
//            case GROUP:
//                return null;
//        }
//
//        ChatSettingModel chatSettingModel = LocalDB.getChatSettingById(JID);
//        if (chatSettingModel == null)
//            chatSettingModel = addChatSetting(JID);
//
//        chatModel.setChatSettingModel(chatSettingModel.isManaged() ? RealmManager.getInstance().getCopy(chatSettingModel) : chatSettingModel);
//
//        RealmManager.getInstance().insertOrUpdate(chatModel);
//        realm.close();
//
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_NEW_CHAT_CREATED, chatModel));
//        XMPPManager.getVCard(JID); // if anonymous person text recieves then fetch its VCARD.
//        return chatModel;
//    }
//
//    public static RecipientModel getRecipientByChatModelId(int id) {
//        return RealmManager.getInstance().getCopy(RealmManager.getInstance()
//                .getData(RecipientModel.class)
//                .equalTo("chatId", id)
//                .findFirst());
//    }
//
//
//    public static void deleteChat(int chatModelId) {
//        ChatModel chatModel = RealmManager.getInstance().getData(ChatModel.class)
//                .equalTo("id", chatModelId)
//                .findFirst();
//
//        ChatModel chatModelCopied = RealmManager.getInstance().getCopy(chatModel);
//        RealmManager.getInstance().delete(chatModel);
//        deleteMessagesByChatId(chatModelId);
//
//        /*Can be used Later , but right now its not needed , so adding in advance*/
//        BaseApplication.getPublishSubject().onNext(new Pair<>(Events.ON_CHAT_DELETED, chatModelCopied));
//    }
//
//    /**
//     * Soft Deleting the responseMessage
//     *
//     * @param msgId
//     */
//    public static void deleteMessageById(String msgId) {
//        MessageModel messageModel = RealmManager.getInstance().getData(MessageModel.class).equalTo("id", msgId).findFirst();
//        Realm realm = openRealmInstance();
//        if (messageModel == null) {
//            closeRealmInstance(realm);
//            return;
//        }
//        messageModel.setDeleted(true);
//        RealmManager.getInstance().insertOrUpdate(messageModel);
////        closeRealmInstance(realm);
//    }
//
//
//    public static boolean deleteMessagesByChatId(int chatModelId) {
//        return RealmManager.getInstance().delete(RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("chatId", chatModelId)
//                .findAll());
//    }
//
//    public static void deleteChats(ArrayList<ChatModel> arrChatModel, boolean keepMedia) {
//
//        if (keepMedia) {
//            for (int i = 0; i < arrChatModel.size(); i++) {
//                RealmManager.getInstance().
//
//                        delete(RealmManager.getInstance().getData(ChatModel.class)
//                                .equalTo("id", arrChatModel.get(i).getId())
//                                .findFirst()
//                        );
//
//                RealmManager.getInstance().delete(RealmManager.getInstance().getData(MessageModel.class)
//                        .equalTo("chatId", arrChatModel.get(i).getId())
//                        .findAll());
//
//            }
//        } else {
//// FIXME: 10/19/2017 Delete from Gallery
//        }
//
//    }
//
//
//    public static int getCount(Class<? extends RealmModel> realmModel) {
//        try {
//            Number number = getDefaultInstance().where(realmModel).count();
//            if (number != null) {
//                return number.intValue() + 1;
//            } else {
//                return 0;
//            }
//        } catch (ArrayIndexOutOfBoundsException e) {
//            return 0;
//        }
//    }
//
//    public static int getStarredMessageCountByChatId(int id) {
//        return RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("chatId", id)
//                .equalTo("isStarred", true)
//                .equalTo("isDeleted", false)
//                .findAll()
//                .size();
//    }
//
//    public static ArrayList<MessageModel> getStarredMessagesByChatId(int id) {
//        ArrayList<MessageModel> messageModels = new ArrayList<>();
//        messageModels.addAll(RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("chatId", id)
//                .equalTo("isStarred", true)
//                .equalTo("isDeleted", false)
//                .findAll()
//        );
//
//        RealmQuery<MessageModel> unseenMessages = RealmManager.getInstance().getData(MessageModel.class)
////                .equalTo("isMyMessage", false)
////                .notEqualTo("status", MessageStatus.SEEN.getValue());
//                .equalTo("chatId", id)
//                .equalTo("isStarred", true);
//
//        RealmResults<MessageModel> results = unseenMessages.distinct("chatId");
//        Integer[] chatIds = new Integer[results.size()];
//        for (int i = 0; i < results.size(); i++) {
//            chatIds[i] = results.get(i).getChatId();
//        }
//
////        return new Pair<>(unseenMessages.findAll(), RealmManager.getInstance().getData(ChatModel.class).in("id", chatIds).findAll());
//
//        return messageModels;
//    }
//
////    public static int getCount(Class<? extends RealmModel> realmQuery , int a){
////        getDefaultInstance().where(realmQuery).distinct().count();
////        return 0;
////    }
//
//    public static void clearDb() {
//        getDefaultInstance()
//                .deleteAll();
//    }
//
//
//    public static RealmResults getMediabyChatId(int chatId) {
//        return RealmManager.getInstance().getData(MessageModel.class)
//                .equalTo("chatId", chatId)
//                .findAll();
////        null;
//    }
//}
