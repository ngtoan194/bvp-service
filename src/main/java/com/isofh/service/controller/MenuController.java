package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.enums.UserType;
import com.isofh.service.enums.WebType;
import com.isofh.service.model.MenuEntity;
import com.isofh.service.utils.CollectionUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/menu")
public class MenuController extends BaseController {

    public MenuController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<MenuEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (MenuEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(MenuEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(MENU, entity);
        return mapData;
    }

    @ApiOperation(value = "Create a menu", notes = "{\"menu\":{}, \"parentId\": 1}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            MenuEntity entity = getObject(MENU, MenuEntity.class);
            long parentId = getLong("parentId", -1L);
            if (parentId != -1) {
                MenuEntity parent = getMenu(parentId);
                entity.setParent(parent);
            }
            menuRepository.save(entity);
            return response(ok(Arrays.asList(MENU, "parent"), Arrays.asList(entity, entity.getParent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update menu", notes = "{\"menu\":{}, \"parentId\": 1}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") Long id) {
        try {
            init(object);

            MenuEntity entity = getMenu(id);
            MenuEntity data = getObject(MENU, MenuEntity.class);

            entity.setName(data.getName());
            entity.setHref(data.getHref());
            entity.setLevel(data.getLevel());
            entity.setRole(data.getRole());
            entity.setIsActive(data.getIsActive());
            entity.setIndex(data.getIndex());

            long parentId = getLong("parentId", -1L);
            if (parentId != -1) {
                MenuEntity parent = getMenu(parentId);
                entity.setParent(parent);
            } else {
                entity.setParent(null);
            }
            menuRepository.save(entity);
            return response(ok(Arrays.asList(MENU, "parent"), Arrays.asList(entity, entity.getParent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete menu", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            init();
            menuRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "search menu", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "level", required = false, defaultValue = DefaultConst.NUMBER) Integer level,
            @RequestParam(value = "isActive", required = false, defaultValue = DefaultConst.NUMBER) Integer isActive,
            @RequestParam(value = "webType", required = false, defaultValue = DefaultConst.NUMBER) Integer webType
    ) {
        try {
            init();
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, "col_index");
            Page<MenuEntity> results = menuRepository.search(name, level, webType, isActive, getPaging(page, size, Sort.by(order)));
            List<Map<String, Object>> mapResults = new ArrayList<>();
            for (MenuEntity entity : results.getContent()) {
                Map<String, Object> mapResult = new HashMap<>();
                mapResults.add(mapResult);
                mapResult.put(MENU, entity);
                mapResult.put(MENUS, entity.getMenus());
            }
            return response(ok(results.getTotalElements(), mapResults));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "lay chi tiet cua menu", notes = "")
    @GetMapping(path = "/get-detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") long id
    ) {
        try {
            init();
            MenuEntity entity = getMenu(id);
            List<Map<String, Object>> mapResults = new ArrayList<>();
            if (!CollectionUtils.isNullOrEmpty(entity.getMenus())) {
                for (MenuEntity menu : entity.getMenus()) {
                    Map<String, Object> mapResult = new HashMap<>();
                    mapResults.add(mapResult);
                    mapResult.put(MENU, menu);
                    mapResult.put(MENUS, menu.getMenus());
                }
            }
            return response(ok(Arrays.asList(MENU, MENUS), Arrays.asList(entity, mapResults)));

        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update menu", notes = "{\"menu\":{}, \"isActive\": 1}")
    @PutMapping(path = "/set-active/{id}")
    public ResponseEntity<?> setActive(@RequestBody Object object, @PathVariable("id") Long id) {
        try {
            init(object);
            MenuEntity entity = getMenu(id);
            Integer isActive = getInteger("isActive");
            if(isActive==1){
                entity.setIsActive(isActive);
            } else {
                entity.setIsActive(isActive);
                entity.setIndex(0);
            }
            menuRepository.save(entity);
            return response(ok(Arrays.asList(MENU, "parent"), Arrays.asList(entity, entity.getParent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "lay chi tiet cua menu", notes = "")
    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "isActive", required = false, defaultValue = "-1") Integer isActive,
            @RequestParam(value = "role", required = false, defaultValue = "-1") Integer role,
            @RequestParam(value = "webType", required = false, defaultValue = "-1") Integer webType
    ) {
        try {
            init();
            if (role != null) {
                if (WebType.BVP.getValue().equals(webType)) {
                    if ((role & UserType.DOCTOR.getValue()) == 0 &&
                            (role & UserType.ADMIN.getValue()) == 0 &&
                            (role & UserType.SUPERADMIN.getValue()) == 0) {
                        role = UserType.USER.getValue();
                    }
                } else if (WebType.HOILAO.getValue().equals(webType)) {
                    if ((role & UserType.ADMIN_HOILAO.getValue()) == 0 &&
                            (role & UserType.MEMBER_HOILAO.getValue()) == 0) {
                        role = UserType.USER.getValue();
                    }
                } else if (WebType.CHONG_LAO.getValue().equals(webType)) {
                    if ((role & UserType.ADMIN_CHONG_LAO.getValue()) == 0) {
                        role = UserType.USER.getValue();
                    }
                }
            }

            List<MenuEntity> results = menuRepository.getAll(isActive, role, webType);
            List<Map<String, Object>> mapMenus = new ArrayList<>();
            for (MenuEntity entity : results) {
                if ((entity.getRole() & role) == 0) {
                    continue;
                }
                if(entity.getIsActive()==1) {
                	 Map<String, Object> mapDataLevel1 = new HashMap<>();
                     mapMenus.add(mapDataLevel1);
                     List<Map<String, Object>> mapDataLevel2s = new ArrayList<>();
                     mapDataLevel1.put(MENU, entity);
                     mapDataLevel1.put(MENUS, mapDataLevel2s);

                     Set<MenuEntity> menuLevel2s = entity.getMenus();
                     for (MenuEntity menuLevel2 : menuLevel2s) {
                         if ((menuLevel2.getRole() & role) == 0) {
                             continue;
                         }
                         if(menuLevel2.getIsActive()==1) {
                        	 Map<String, Object> mapDataLevel2 = new HashMap<>();
                             mapDataLevel2s.add(mapDataLevel2);

                             Set<MenuEntity> menuLevel3s = menuLevel2.getMenus();
                             List<MenuEntity> listMenuLevel3 = new ArrayList<>();

                             mapDataLevel2.put(MENU, menuLevel2);
                             mapDataLevel2.put(MENUS, listMenuLevel3);

                             for (MenuEntity menuLevel3 : menuLevel3s) {
                                 if ((menuLevel3.getRole() & role) == 0) {
                                     continue;
                                 }
                                 if(menuLevel3.getIsActive()==1) {
                                	 listMenuLevel3.add(menuLevel3);
                                 }
                             }
                         }
                     }
                }
               
            }
            return response(ok(MENUS, mapMenus));

        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    private List<Map<String, Object>> getData(List<MenuEntity> menus, Integer role) {
        List<Map<String, Object>> mapMenus = new ArrayList<>();
        for (MenuEntity entity : menus) {
            if (role != null && (entity.getRole() & role) == 0) {
                continue;
            }

            Map<String, Object> mapDataLevel1 = new HashMap<>();
            mapMenus.add(mapDataLevel1);
            List<Map<String, Object>> mapDataLevel2s = new ArrayList<>();
            mapDataLevel1.put(MENU, entity);
            mapDataLevel1.put(MENUS, mapDataLevel2s);

            Set<MenuEntity> menuLevel2s = entity.getMenus();
            for (MenuEntity menuLevel2 : menuLevel2s) {
                if (role != null && (menuLevel2.getRole() & role) == 0) {
                    continue;
                }

                Map<String, Object> mapDataLevel2 = new HashMap<>();
                mapDataLevel2s.add(mapDataLevel2);

                Set<MenuEntity> menuLevel3s = menuLevel2.getMenus();
                List<MenuEntity> listMenuLevel3 = new ArrayList<>();

                mapDataLevel2.put(MENU, menuLevel2);
                mapDataLevel2.put(MENUS, listMenuLevel3);

                for (MenuEntity menuLevel3 : menuLevel3s) {
                    if (role != null && (menuLevel3.getRole() & role) == 0) {
                        continue;
                    }
                    listMenuLevel3.add(menuLevel3);
                }
            }
        }
        return mapMenus;
    }

    @ApiOperation(value = "Set Group", notes = "")
    @PutMapping(path = "/set-group/{id}")
    public ResponseEntity<?> setGroup(@RequestBody Object object, @PathVariable("id") Long id) {
        try {
            init(object);
            MenuEntity entity = getMenu(id);
            Integer group = getInteger("group");
            entity.setGroup(group);
            save(entity);
            return response(ok(Arrays.asList(MENU), Arrays.asList(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Get By Group", notes = "")
    @PutMapping(path = "/get-by-group/{group}")
    public ResponseEntity<?> getByGroup(@PathVariable("group") Integer group) {
        try {
            init();
            List<MenuEntity> menus = menuRepository.getByGroup(group);
            return response(ok(MENUS, CollectionUtils.isNullOrEmpty(menus) ? null : menus.get(0).getMenus()));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            MenuEntity entity = getObject("menu", MenuEntity.class);
            LocalDateTime createdDate = entity.getCreatedDate();
            save(entity);
            if (createdDate != null) {
                entity.setCreatedDate(createdDate);
                save(entity);
            }
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-parent", notes = "")
    @PostMapping(path = "/update-parent")
    public ResponseEntity<?> updateConference(@RequestBody Object object) {
        try {
            init(object);
            String menuUid = getString("menuUid");
            String menuParentUid = getString("menuParentUid");
            MenuEntity menu = menuRepository.findFirstByUid(menuUid);
            MenuEntity menuParent = menuRepository.findFirstByUid(menuParentUid);
            if (menu == null || menuParent == null) {
                return response(error(new Exception("menu null or menuParent null")));
            }
            menu.setParent(menuParent);
            save(menu);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
}
