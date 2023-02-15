package controller;

import model.OnBoardDTO;

import java.util.ArrayList;

public class OnBoardController {
    private ArrayList<OnBoardDTO> list;
    private int nextId;

    public OnBoardController() {
        list = new ArrayList<>();
        nextId = 1;

        addOnBoard();

    }

    private void addOnBoard(){
        for (int i = 1; i <= 3; i++){
            OnBoardDTO on = new OnBoardDTO();
            on.setFilmId(i);
            on.setTheaterId(1);
            on.setRunningTime("11시 ~ 13시");
            add(on);
        }

        for (int i = 3; i <= 4; i++){
            OnBoardDTO on = new OnBoardDTO();
            on.setFilmId(i);
            on.setTheaterId(2);
            on.setRunningTime("13시 ~ 15시");
            add(on);
        }

        for (int i = 1; i <= 3; i++){
            OnBoardDTO on = new OnBoardDTO();
            on.setFilmId(i);
            on.setTheaterId(3);
            on.setRunningTime("19시 ~ 21시");
            add(on);
        }

        for (int i = 2; i <= 4; i++){
            OnBoardDTO on = new OnBoardDTO();
            on.setFilmId(i);
            on.setTheaterId(4);
            on.setRunningTime("21시 ~ 23시");
            add(on);
        }
    }

    public void add(OnBoardDTO onBoardDTO) {
        onBoardDTO.setId(nextId++);
        list.add(onBoardDTO);
    }

    public ArrayList<OnBoardDTO> selectAll() {
        ArrayList<OnBoardDTO> temp = new ArrayList<>();
        for (OnBoardDTO r : list) {
            temp.add(new OnBoardDTO(r));

        }
        return temp;
    }

    public ArrayList<OnBoardDTO> selectAll(int theaterId) {
        ArrayList<OnBoardDTO> temp = new ArrayList<>();
        for (OnBoardDTO r : list) {
            if (r.getTheaterId() == theaterId) {
                temp.add(new OnBoardDTO(r));
            }
        }
        return temp;
    }

    public OnBoardDTO selectOne(int id) {
        OnBoardDTO on = new OnBoardDTO();
        on.setId(id);
        if (list.contains(on)) {
            return new OnBoardDTO(list.get(list.indexOf(on)));

        } else {
            return null;
        }
    }

    public void update(OnBoardDTO onBoardDTO) {
        list.set(list.indexOf(onBoardDTO), onBoardDTO);
    }

    public void delete(int id) {
        OnBoardDTO on = new OnBoardDTO();
        on.setId(id);
        list.remove(on);
    }



}
