package com.sammaru5.sammaru.domain;

public enum IndelibleBoardName {
    공지사항,
    사진첩,
    족보;

    public static boolean contain(String boardname) {
        for (IndelibleBoardName indelibleBoardName : IndelibleBoardName.values()) {
            if(indelibleBoardName.toString().equals(boardname)){
                return true;
            }
        }
        return false;
    }

}
