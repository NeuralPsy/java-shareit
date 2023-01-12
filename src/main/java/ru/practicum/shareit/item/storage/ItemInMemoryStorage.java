package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemInMemoryStorage {

    private static int itemId = 1;
    private static final Map<Integer, Integer> itemOwnerMap = new HashMap<>();

    private static final Map<Integer, Item> items = new HashMap<>();


    public ItemDto addItem(Item item, Integer ownerId) {
        item.setItemId(itemId++);
        items.put(item.getItemId(), item);
        itemOwnerMap.put(item.getItemId(), ownerId);

        return ItemMapper.itemToDto(item);
    }

    public void updateItem(Item item) {
        items.put(item.getItemId(), item);
    }

    public Collection<Integer> getUserIds() {
        return itemOwnerMap.values();
    }


    public Integer getUserIdByItemId(Integer itemId) {
        return itemOwnerMap.get(itemId);
    }

    public Item getItemById(Integer itemId) {
        Item item = items.get(itemId);
        return item;
    }

    public Collection<Item> getAllOwnersItems(Integer ownerId) {
        List<Item> itemsToReturn = new ArrayList<>();
        itemOwnerMap.keySet().forEach(key -> {
            if (itemOwnerMap.get(key).equals(ownerId)) {
                itemsToReturn.add(items.get(key));
            }
        });

        return itemsToReturn;
    }

    public Collection<Item> findItemByWord(String text) {

        String lowerText = text.toLowerCase();

        if (lowerText.equals("")) return items.values()
                .stream()
                .filter(item -> item.getDescription().equals("") || item.getName().equals(""))
                .collect(Collectors.toList());

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
