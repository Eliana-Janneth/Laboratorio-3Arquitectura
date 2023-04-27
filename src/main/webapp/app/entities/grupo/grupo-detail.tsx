import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './grupo.reducer';

export const GrupoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const grupoEntity = useAppSelector(state => state.grupo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="grupoDetailsHeading">
          <Translate contentKey="labArquiJHipsterApp.grupo.detail.title">Grupo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{grupoEntity.id}</dd>
          <dt>
            <span id="grupoId">
              <Translate contentKey="labArquiJHipsterApp.grupo.grupoId">Grupo Id</Translate>
            </span>
          </dt>
          <dd>{grupoEntity.grupoId}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="labArquiJHipsterApp.grupo.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{grupoEntity.codigo}</dd>
          <dt>
            <span id="horario">
              <Translate contentKey="labArquiJHipsterApp.grupo.horario">Horario</Translate>
            </span>
          </dt>
          <dd>{grupoEntity.horario}</dd>
          <dt>
            <Translate contentKey="labArquiJHipsterApp.grupo.curso">Curso</Translate>
          </dt>
          <dd>{grupoEntity.curso ? grupoEntity.curso.id : ''}</dd>
          <dt>
            <Translate contentKey="labArquiJHipsterApp.grupo.semestreAcademico">Semestre Academico</Translate>
          </dt>
          <dd>{grupoEntity.semestreAcademico ? grupoEntity.semestreAcademico.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/grupo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/grupo/${grupoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GrupoDetail;
