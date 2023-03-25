package com.example.mdbspringboot.Controller;
import com.example.mdbspringboot.model.GroceryItem;
import com.example.mdbspringboot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private ItemRepository groceryItemRepo;
    @RequestMapping("")
    public String homepage(){
        return "Home Page";
    }

    @PostMapping("/create")
    public GroceryItem createItem (@RequestBody GroceryItem groceryItem){
        return groceryItemRepo.save(groceryItem);

    }
     @GetMapping("/{id}")
    public GroceryItem getItemByName (@PathVariable("id") String id) {
         return groceryItemRepo.findItemById(id);
//         return ResponseEntity.ok().body(groceryItemRepo.findItemById(id));
     }
//    @GetMapping("/{name}")
//    public GroceryItem getItemById (@PathVariable("name") String name) {
//        return groceryItemRepo.findItemByName(name);
//    }
     @GetMapping("/items")
     @CrossOrigin(origins = "http://localhost:3000")
    public List<GroceryItem> getAllItems(){
        return  groceryItemRepo.findAll();
     }
//    @PutMapping("/add/{id}/{amount}")
@PutMapping("/add/{id}/{amount}")
    public ResponseEntity<GroceryItem> addQuantity(@PathVariable("id") String id, @PathVariable("amount") Integer amount){
        Optional<GroceryItem> itemData = Optional.ofNullable(groceryItemRepo.findItemById(id));

        if (itemData.isPresent()){
            GroceryItem item = itemData.get();
            item.setQuantity(amount);
            return new  ResponseEntity<>(groceryItemRepo.save(item), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
@DeleteMapping("/delete/{id}")
    public void deleteItem(@PathVariable("id") String id){
        Optional<GroceryItem> itemData = Optional.ofNullable(groceryItemRepo.findItemById(id));

        if(itemData.isPresent()){
             groceryItemRepo.deleteById(id);
        }

}
}
