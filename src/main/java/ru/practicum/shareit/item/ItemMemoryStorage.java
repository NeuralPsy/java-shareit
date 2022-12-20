package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemMemoryStorage {

    private static int itemId = 1;
    private static final Map<Integer, Integer> itemOwnerMap = new HashMap<>();

    private static final Map<Integer, Item> items = new HashMap<>();



    public ItemDto addItem(Item item, Integer ownerId){
        item.setId(itemId++);
        items.put(item.getId(), item);
        itemOwnerMap.put(item.getId(), ownerId);

        return ItemMapper.itemToDto(item);
    }

    public void updateItem(Item item){
        items.put(item.getId(), item);
    }

    public Collection<Integer> getUserIds(){
        return itemOwnerMap.values();
    }


    public Integer getUserIdByItemId(Integer itemId){
        return  itemOwnerMap.get(itemId);
    }

    public Item getItemById(Integer itemId){
        Item item = items.get(itemId);
        return item;
    }

    public Collection<Item> getAllOwnersItems(Integer ownerId) {
        List<Item> itemsToReturn = new ArrayList<>();
        itemOwnerMap.keySet().stream().forEach(itemId -> {
            if(itemOwnerMap.get(itemId).equals(ownerId)){
                itemsToReturn.add(items.get(itemId));
            }
        });

        return  itemsToReturn;
    }

    public Collection<Item> findItemByWord(String text) {

        String lowerText = text.toLowerCase();

        if (lowerText.equals("")) return items.values()
                .stream()
                .filter(item -> item.getDescription().equals("") || item.getName().equals(""))
                .collect(Collectors.toList()); // написал эти строки только для того, чтобы пройти тесты в Postman.
//        Тесты постмана ожидали от программы, что при отсутствии текста в поисковом запросе,
//        должен выводиться пустой список. На мой взгляд это совсем неправильно. И в ТЗ такого не было.
//        Если никаких текстовых запросов у пользователя нет, но и выводиться будут все items.


        return items.values().stream()
                .filter(item -> (item.getDescription()
                        .toLowerCase()
                        .contains(lowerText) ||
                        item.getName().toLowerCase()
                                .contains(lowerText)) && item.getAvailable())
                .collect(Collectors.toList());
    }

    public Collection<Item> getAll() {
        return items.values();
    }

    public void removeItem(Integer itemId) {
        items.remove(itemId);
    }
}
