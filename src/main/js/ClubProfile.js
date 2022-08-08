import React, {useState, useEffect} from 'react';
import { useParams } from "react-router-dom";
import { render } from 'react-dom';
import "./App.js";
import "./ClubProfile.css";
import ClubProfileDescription from './ClubProfileDescription';
import ClubService from './ClubService.js';

const ClubProfile = () => {

    const [clubInfo, setClubInfo] = useState(null);

    let {clubId} = useParams();     
    
    useEffect(() => {                                                       
        ClubService.doClubInfo(clubId).then(response => {

            console.log(response.status);

            if(response.status != 200) {
                throw "Backend is Not Responding!"
            }

            return response.json();
        }).then(json => {
            setClubInfo(json);
        }).catch(err => {console.log("backend is not responding")})
    }, [clubId]);


    return(
        <>
        <ClubProfileDescription clubInfo = {clubInfo} setClubInfo = {setClubInfo} />
        </>
    );
}
export default ClubProfile;