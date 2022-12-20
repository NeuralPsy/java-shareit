package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemServiceImpl service;

    @Autowired
    public ItemController(ItemServiceImpl service){
        this.service = service;
    }

    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Integer userId){
        return service.addItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto editItem(@PathVariable Integer itemId, @RequestHeader("X-Sharer-User-Id") Integer ownerId,
                            @RequestBody ItemDto itemDto){
        return service.editItem(itemId, ownerId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Integer itemId){
        return service.getItemById(itemId);
    }

    @GetMapping
    public Collection<ItemDto> getAllOwnersItems(@RequestHeader("X-Sharer-User-Id") Integer ownerId){
        return service.getAllOwnersItems(ownerId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> findItemByWord(@RequestParam("text") String text){
        return service.findItemByWord(text);
    }

    @DeleteMapping("/{itemId}")
    public String removeItem(@PathVariable Integer itemId){
        return service.removeItem(itemId);
    }


}
