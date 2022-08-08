import React, {useState, useEffect} from 'react';
import "./ClubEditor.css";
import 'antd/dist/antd.css';
import Modal from 'react-modal';
import ClubService from './ClubService.js';
import AddTagModal from './AddTagModal';
import {
        PlusOutlined,
        CloseOutlined
        } from "@ant-design/icons";

const ClubEditorTags = (props) => {
    const [tagModalIsOpen, setTagModalIsOpen] = useState(false);

    const onTagRemove = (e) => {
        ClubService.doRemoveTag(e).then(response => {props.setRefresh(true)});
    }
    const onTagAdd = () => {
        setTagModalIsOpen(true);
    }
    
    return(
        <>   
        <div className="clubProfileTagDiv">
            <Modal style = {{content: {"background" : "#3A4750", "overflow" : "scroll", "borderRadius" : "25px"}}} isOpen = {tagModalIsOpen} onRequestClose = {() => {setTagModalIsOpen(false)}}><AddTagModal  clubTags = {props.clubTags} setRefresh = {props.setRefresh} /></Modal>
            <div className="clubProfileTagDiv">
                {props.clubTags.map(clubTag=>(
                        <a key = {clubTag} className = "displayTagRed">{clubTag}<CloseOutlined onClick = {() => onTagRemove(clubTag)} style={{marginLeft: "10px"}}/></a>
                ))}
                    <a className="displayTagRed" style={{textAlign: "center"}} onClick = {onTagAdd}><PlusOutlined/></a> 
            </div>
        </div>      
        </>
    );
}
export default ClubEditorTags;