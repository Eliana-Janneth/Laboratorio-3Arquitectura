import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/estudiante">
        <Translate contentKey="global.menu.entities.estudiante" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/profesor">
        <Translate contentKey="global.menu.entities.profesor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/curso">
        <Translate contentKey="global.menu.entities.curso" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/semestre-academico">
        <Translate contentKey="global.menu.entities.semestreAcademico" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/grupo">
        <Translate contentKey="global.menu.entities.grupo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/actividad">
        <Translate contentKey="global.menu.entities.actividad" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/grupo-estudiante">
        <Translate contentKey="global.menu.entities.grupoEstudiante" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nota">
        <Translate contentKey="global.menu.entities.nota" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/auditoria-nota">
        <Translate contentKey="global.menu.entities.auditoriaNota" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
