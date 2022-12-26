package ru.practicum.shareit.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

/**
 * The class representing endpoints to manipulate items
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemServiceImpl service;

    @Autowired
    public ItemController(ItemServiceImpl service) {
        this.service = service;
    }

    /**
     * @param itemDto JSON object of DTO item that comes from request body
     * @param userId comes from request header "X-Sharer-User-Id"
     * @return DTO object of item with ID is returned when added into storage
     */
    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return service.addItem(itemDto, userId);
    }

    /**
     * @param itemId comes from path as variable. This is ID of item that user wants to edit (update)
     * @param ownerId comes from request header "X-Sharer-User-Id"
     * @param itemDto JSON object of new version of DTO item that comes from request body
     * @return DTO object of updated item is returned when added into storage
     */
    @PatchMapping("/{itemId}")
    public ItemDto editItem(@PathVariable Integer itemId, @RequestHeader("X-Sharer-User-Id") Integer ownerId,
                            @RequestBody ItemDto itemDto) {
        return service.editItem(itemId, ownerId, itemDto);
    }

    /**
     * @param itemId comes from path as variable. This is ID of item that user wants to get
     * @return DTO object item is returned in accordance with it's ID (itemId param)
     */
    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Integer itemId) {
        return service.getItemById(itemId);
    }

    /**
     * @param ownerId comes from request header "X-Sharer-User-Id"
     * @return collection of DTO items of a user whose ID equals ownerId param that comes from request header
     */
    @GetMapping
    public Collection<ItemDto> getAllOwnersItems(@RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        return service.getAllOwnersItems(ownerId);
    }

    /**
     * @param text comes from path as a request param
     * @return collection of DTO items that contain text from text param in it's name or description
     */
    @GetMapping("/search")
    public Collection<ItemDto> findItemByWord(@RequestParam("text") String text) {
        return service.findItemByWord(text);
    }

    /**
     * @param itemId comes from path as variable. This is ID of item that user wants to delete
     * @return String object that contains message of successfully deleted item
     */
    @DeleteMapping("/{itemId}")
    public String removeItem(@PathVariable Integer itemId) {
        return service.removeItem(itemId);
    }


}
