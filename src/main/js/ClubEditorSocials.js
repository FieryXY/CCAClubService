import React, {useState, useEffect} from 'react';
import "./ClubEditor.css";
import 'antd/dist/antd.css';
import Modal from 'react-modal';
import ClubService from './ClubService';
import SocialModal from './SocialModal';
import {
        InstagramOutlined,
        MailOutlined,
        IdcardOutlined,
        LinkOutlined
        } from "@ant-design/icons";
const ClubEditorSocials = (props) => {
        const [clubwebsite, setClubWebsite] = useState(null);
        const [instagram, setInstagram] = useState(null);
        const [email, setEmail] = useState(null);
        const [classroom, setClassroom] = useState(null);
        const [other, setOther] = useState(null);
        const [currentSocial, setCurrentSocial] = useState(null);
        useEffect(() => {
                setClubWebsite(null);
                setInstagram(null);
                setEmail(null);
                setClassroom(null);
                setOther(null);

                for(const social of props.clubSocials) {
                        if(social["socialName"] === "Club Website") setClubWebsite(social["socialLink"]);
                        if(social["socialName"] === "Instagram") setInstagram(social["socialLink"]);
                        if(social["socialName"] === "Email") setEmail("mailto:" + social["socialLink"]);
                        if(social["socialName"] === "Classroom") setClassroom(social["socialLink"]);
                        if(social["socialName"] === "Other") setOther(social["socialLink"]);
        }}, [props.clubSocials])
        const [socialModalIsOpen, setSocialModalIsOpen] = useState(false);
        const onSocialAdd = (social) => {
                setCurrentSocial(social);
                setSocialModalIsOpen(true);
        }
        
        return(
        <>      
                <Modal style = {{content: {"background" : "#3A4750", "overflow" : "scroll", "borderRadius" : "25px"}}} isOpen = {socialModalIsOpen} onRequestClose = {() => {
                                setSocialModalIsOpen(false);
                                setCurrentSocial(null);           
                        }}>
                        <SocialModal setSocialModalIsOpen={setSocialModalIsOpen} socialMap = {{"Club Website" : clubwebsite, "Instagram" : instagram, "Email" : email.substring(7), "Classroom": classroom, "Other" : other}} setRefresh = {props.setRefresh}
                                                        setCurrentSocial = {setCurrentSocial} currentSocial = {currentSocial} setClubSocials = {props.setClubSocials}/>
                </Modal>
                <div className="linkShelf">
                        <a className= {(clubwebsite === null) ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Club Website")}}><IdcardOutlined style= {{color: '#FFFFFF'}} href = {clubwebsite}/></a>
                        <a className={(instagram === null) ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Instagram")}}><InstagramOutlined style= {{color: '#FFFFFF'}} href = {instagram}/></a> 
                        <a className={(email === null) ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Email")}}><MailOutlined style= {{color: '#FFFFFF'}} href = {email}/></a>
                        <a className = {(classroom === null) ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Classroom")}}><img style={{height: "85%"}} src = {require('./img/googleclassroomlogo.png').default}/></a>
                        <a className = {(other === null) ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Other")}}><LinkOutlined style= {{color: '#FFFFFF'}} href = {other}/></a>
                </div>        
        </>
        );
}
export default ClubEditorSocials;