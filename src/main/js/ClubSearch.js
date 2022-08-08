import React, {useState} from 'react';
import {useEffect} from 'react';
import "./ClubSearch.css";
import ClubSearchBar from "./ClubSearchBar.js";
import ClubTagBox from "./ClubTagBox.js";
import ClubResults from "./ClubResults.js";
import ClubService from './ClubService.js';

const editDistanceThreshold = 5;

const ClubSearchPage = () => {
    
    const [clubSearchQuery, setClubSearchQuery] = useState("");
    const [allClubTags, setAllClubTags] = useState([]);
    const [clubTagFilters, setClubTagFilters] = useState([]);
    const [clubResultList, setClubResultList] = useState([]);
    const [allClubs, setAllClubs] = useState([]);

    //Calculate the Damerau-Levenshtein distance between two strings (I have no clue how this works)
        const calculateDistance = (str1, str2) => {
            let distanceMatrix = [];
            for (let i = 0; i <= str2.length; i++) {
                let row = [i];
                distanceMatrix.push(row);
            }
            let firstRow = distanceMatrix[0];
            for (let j = 0; j <= str1.length; j++) {
                firstRow.push(j);
            }
            for (let i = 1; i <= str2.length; i++) {
                for (let j = 1; j <= str1.length; j++) {
                    if (str2.charAt(i-1) == str1.charAt(j-1)) {
                        distanceMatrix[i][j] = distanceMatrix[i-1][j-1];
                    } else {
                        distanceMatrix[i][j] = Math.min(distanceMatrix[i-1][j-1] + 1, // substitution
                                                        distanceMatrix[i][j-1] + 1, // insertion
                                                        distanceMatrix[i-1][j] + 1); // deletion
                    }
                    if (i > 1 && j > 1 && str2.charAt(i-1) == str1.charAt(j-2) && str2.charAt(i-2) == str1.charAt(j-1)) {
                        distanceMatrix[i][j] = Math.min(distanceMatrix[i][j], distanceMatrix[i-2][j-2] + 1); // transposition
                    }
                }
            }
            return distanceMatrix[str2.length][str1.length];
        }

        const filterClubs = () => {
                let filteredClubs = allClubs;
                if(clubTagFilters.length > 0) {
                    filteredClubs = filteredClubs.filter(club => {
                        return clubTagFilters.every(tag => club["clubCategories"].includes(tag));
                    });
                }
                if(clubSearchQuery !== "") {
                    // filteredClubs = filteredClubs.filter(club => {
                    //     return club["clubName"].toLowerCase().includes(clubSearchQuery.toLowerCase());
                    // });
                    filteredClubs = filteredClubs.filter(club => {
                        return calculateDistance(club["clubName"].toLowerCase(), clubSearchQuery.toLowerCase()) <= editDistanceThreshold || club["clubName"].toLowerCase().includes(clubSearchQuery.toLowerCase());
                    });
                }
                //Sort clubs by distance from search query in increasing order
                filteredClubs.sort((club1, club2) => {
                    return calculateDistance(clubSearchQuery, club1["clubName"]) - calculateDistance(clubSearchQuery, club2["clubName"]);
                });
                setClubResultList(filteredClubs);
            }
    
    useEffect(() => {
        ClubService.doClubList().then(response => {
            if(response["status"] != 200) {
                throw "Backend is not responding"
            }
            return response.json();
        }).then(json => {
            setAllClubs(json);
            filterClubs();
        }).catch(err => {console.log("backend is not responding")})    
    }, []);

    useEffect(() => {
        ClubService.doClubTags().then(response => {
            if(response["status"] != 200) {
                throw "Backend is not responding"
            }
            return response.json();
        }).then(json => {
            setAllClubTags(json);
        }).catch(err => {console.log("backend is not responding")})}, []);

        useEffect(() => {
            filterClubs();
        }, [allClubs]);

        useEffect(() => {
            filterClubs();
        }, [clubSearchQuery, clubTagFilters]);


    return (
        <>
        
        <br/><br/><br/><br/><br/>
        <div className="container" id="container">
           
                
                <h1 className="clubSearchPageH1" style={{textAlign: "center"}}>Search for Clubs!</h1>
                 <ClubSearchBar clubSearchQuery = {clubSearchQuery} setClubSearchQuery = {setClubSearchQuery}/>
                <div className="row">
                <ClubTagBox allClubTags = {allClubTags} setAllClubTags = {setAllClubTags} clubTagFilters = {clubTagFilters} setClubTagFilters = {setClubTagFilters}/>
            
                <ClubResults clubResultList = {clubResultList}/>
                </div>
               
       
        </div>
        
      </>
    );
}

export default ClubSearchPage;