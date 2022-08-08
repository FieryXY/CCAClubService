import React, {useState, useEffect} from 'react';
import "./ClubEditor.css";
import Modal from 'react-modal';
import PfpModal from './PfpModal';
import ClubService from './ClubService';
import {Buffer} from 'buffer';

const ClubEditorPfp = (props) => {
    const [pfpSelectorOpen, setPfpSelectorOpen] = useState(false);
    
    const base64ToBlob = (base64) => {
        let bytes = new Uint8Array(Buffer.from(base64.split(',')[1], 'base64'));
        return new Blob([bytes], {type: base64.split(';')[0].split(':')[1]});
    }


    const handleFile = (blob) => {
        ClubService.doChangeImage(blob).then(response => {props.setRefresh(true)});
        setPfpSelectorOpen(false);
    }
    
    
    return(
        <>

        <div style={{position: "relative", width:"50%"}}>
        <img className="clubProfilePicture" src = {(props.clubPfp == null) ? require("./img/ccalogo.png").default : props.clubPfp} onClick = {() => {
                setPfpSelectorOpen(true);
        }} />
           <div className="editOverlay" onClick = {() => {
                setPfpSelectorOpen(true);
        }}>Edit</div>
        </div>
        <Modal style = {{content: {"background" : "#3A4750", "overflow" : "scroll", "borderRadius" : "25px"}}} isOpen={pfpSelectorOpen} onRequestClose = {() => {setPfpSelectorOpen(false)}}>
            <PfpModal
            onFileSelectError={({ error }) => alert(error)} 
             setRefresh = {props.setRefresh}
            setPfpSelectorOpen = {setPfpSelectorOpen} handleFile = {handleFile}/>
        </Modal>
        </> 
    );
}
export default ClubEditorPfp;