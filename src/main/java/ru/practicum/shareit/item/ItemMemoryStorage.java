package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemMemoryStorage {

    private static int itemId = 1;
    private static final Map<Integer, Integer> itemOwnerMap = new HashMap<>();

    private static final List<Item> items = new ArrayList<>();



    public ItemDto addItem(Item item, Integer ownerId){
        item.setId(itemId++);
        items.add(item);
        itemOwnerMap.put(item.getId(), ownerId);

        return ItemMapper.itemToDto(item);
    }

    public void updateItem(Item item, Integer ownerId){
        items.add(item.getId(), item);
    }

    public Collection<Integer> getUserIds(){
        return itemOwnerMap.values();
    }


    public Integer getUserIdByItemId(Integer itemId){
        return  itemOwnerMap.get(itemId);
    }

    public Item getItemById(Integer itemId){
        Item item = items.stream()
                .filter(it -> itemId.equals(it.getId()))
                .collect(Collectors.toList())
                .get(0);
        return item;
    }

    public Collection<Item> getAllOwnersItems(Integer ownerId) {
        return items.stream()
                .filter(item -> ownerId.equals(item.getOwner().getId()))
                .collect(Collectors.toList());
    }

    public Collection<Item> findItemByWord(String text) {
        return items.stream()
                .filter(item -> item.getDescription()
                        .toLowerCase()
                        .contains(text.toLowerCase()) ||
                        item.getName().toLowerCase()
                                .contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<Item> getAll() {
        return items;
    }

    public void removeItem(Integer itemId) {
        items.forEach(item -> {
            if (itemId.equals(item.getId())) items.remove(item);
        });
    }
}
